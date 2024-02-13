package run.freshr.service;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.util.Objects.isNull;
import static run.freshr.common.kafka.KafkaEvent.LOGGING_ACCOUNT_INFO;
import static run.freshr.common.kafka.KafkaTopic.LOGGING_CREATE;
import static run.freshr.common.utils.CryptoUtil.decryptRsa;
import static run.freshr.common.utils.MapperUtil.map;
import static run.freshr.common.utils.RestUtilAuthAware.checkManager;
import static run.freshr.common.utils.RestUtilAuthAware.getSigned;
import static run.freshr.common.utils.RestUtilAuthAware.getSignedId;
import static run.freshr.common.utils.RestUtilAware.buildId;
import static run.freshr.common.utils.RestUtilAware.checkProfile;
import static run.freshr.common.utils.RestUtilAware.error;
import static run.freshr.common.utils.RestUtilAware.getConfig;
import static run.freshr.common.utils.RestUtilAware.getExceptions;
import static run.freshr.common.utils.RestUtilAware.ok;
import static run.freshr.common.utils.StringUtil.padding;
import static run.freshr.common.utils.StringUtil.uuidWithoutHyphen;
import static run.freshr.domain.account.enumerations.AccountStatus.ACTIVE;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.data.CursorData;
import run.freshr.common.data.IdDocumentData;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.dto.response.IsResponse;
import run.freshr.common.kafka.KafkaData;
import run.freshr.common.kafka.KafkaProducer;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.dto.response.AccountByManagerResponse;
import run.freshr.domain.account.dto.response.AccountFollowerListResponse;
import run.freshr.domain.account.dto.response.AccountFollowingListResponse;
import run.freshr.domain.account.dto.response.AccountListByManagerResponse;
import run.freshr.domain.account.dto.response.AccountListResponse;
import run.freshr.domain.account.dto.response.AccountNotificationListResponse;
import run.freshr.domain.account.dto.response.AccountResponse;
import run.freshr.domain.account.dto.response.AccountResponseExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.AccountNotification;
import run.freshr.domain.account.entity.embedded.AccountFollowId;
import run.freshr.domain.account.entity.mapping.AccountFollow;
import run.freshr.domain.account.entity.mapping.AccountHashtag;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.unit.jpa.AccountFollowUnit;
import run.freshr.domain.account.unit.jpa.AccountHashtagUnit;
import run.freshr.domain.account.unit.jpa.AccountNotificationUnit;
import run.freshr.domain.account.unit.jpa.AccountUnit;
import run.freshr.domain.account.unit.jpa.CycleUnit;
import run.freshr.domain.account.vo.AccountSearch;
import run.freshr.domain.auth.enumerations.Privilege;
import run.freshr.domain.auth.redis.ChatAccessRedis;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.unit.redis.ChatAccessRedisUnit;
import run.freshr.domain.auth.unit.redis.RsaPairUnit;
import run.freshr.domain.logging.bus.LogAccountBus;
import run.freshr.domain.logging.elasticsearch.data.LogAccountData;
import run.freshr.domain.logging.enumerations.LogAccountType;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.unit.jpa.PredefinedValidUnit;
import run.freshr.rocketchat.client.RocketChatUserClient;
import run.freshr.rocketchat.data.RocketChatUserData;
import run.freshr.rocketchat.dto.request.RocketChatCreateUserRequest;
import run.freshr.rocketchat.dto.response.RocketChatCreateUserResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

  private final KafkaProducer producer;

  private final RocketChatUserClient rocketChatUserClient;

  private final ChatAccessRedisUnit chatAccessRedisUnit;
  private final RsaPairUnit rsaPairUnit;

  private final CycleUnit cycleUnit;

  private final AccountUnit accountUnit;
  private final AccountHashtagUnit accountHashtagUnit;
  private final AccountFollowUnit accountFollowUnit;
  private final AccountNotificationUnit accountNotificationUnit;

  private final PredefinedValidUnit predefinedValidUnit;

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  @Override
  public ResponseEntity<?> existsAccountByUsername(AccountSearch search) {
    log.info("AccountService.existsAccountByUsername");

    String username = search.getWord();
    Boolean exists = accountUnit.existsByUsername(username);

    return ok(IsResponse.builder().is(exists).build());
  }

  @Override
  public ResponseEntity<?> existsAccountByNickname(AccountSearch search) {
    log.info("AccountService.existsAccountByNickname");

    String nickname = search.getWord();
    Boolean exists = accountUnit.existsByNickname(nickname);

    return ok(IsResponse.builder().is(exists).build());
  }

  @Override
  @Transactional
  public ResponseEntity<?> join(AccountJoinRequest dto) {
    log.info("AccountService.join");

    String encodePublicKey = dto.getRsa();

    // RSA 유효 기간 체크
    if (!rsaPairUnit.checkRsa(encodePublicKey, getConfig().getRsaLimit().longValue())) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();
    String username = decryptRsa(dto.getUsername(), encodePrivateKey);

    // 요청 정보로 데이터가 있는지 체크
    if (accountUnit.existsByUsername(username)) {
      return error(getExceptions().getDuplicate());
    }

    IdRequest<Long> profileDto = dto.getProfile();

    Attach profile = null;

    boolean profileValid = false;

    if (!isNull(profileDto) && !isNull(profileDto.getId())) {
      profileValid = predefinedValidUnit.validateAttach(profileDto.getId());
    }

    if (profileValid) {
      profile = predefinedValidUnit.getAttach(profileDto.getId());

      profile.changeIsPublic();
    }

    String password = decryptRsa(dto.getPassword(), encodePrivateKey);
    String nickname = decryptRsa(dto.getNickname(), encodePrivateKey);
    String uuid;

    if (!checkProfile("test")) {
      String authToken, userId;

      try {
        ChatAccessRedis managerChatAccess = chatAccessRedisUnit.getManager();

        authToken = managerChatAccess.getToken();
        userId = managerChatAccess.getId();
      } catch (HttpException e) {
        return error(getExceptions().getError(), e.getMessage());
      }

      ResponseEntity<RocketChatCreateUserResponse> rocketChat = rocketChatUserClient.createUser(
          authToken,
          userId,
          RocketChatCreateUserRequest
              .builder()
              .username(username)
              .email(username)
              .name(nickname)
              .password(password)
              .joinDefaultChannels(false)
              .build());
      HttpStatusCode rocketChatStatusCode = rocketChat.getStatusCode();

      if (rocketChatStatusCode.isError()) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      RocketChatCreateUserResponse rocketChatBody = rocketChat.getBody();

      if (isNull(rocketChatBody)) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      Boolean rocketChatSuccess = rocketChatBody.getSuccess();

      if (rocketChatSuccess) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      RocketChatUserData rocketChatData = rocketChatBody.getUser();

      if (isNull(rocketChatData)) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      uuid = rocketChatData.getId();
    } else {
      uuid = uuidWithoutHyphen();
    }

    Account entity = Account
        .builder()
        .privilege(Privilege.USER)
        .uuid(uuid)
        .username(username)
        .password(password)
        .nickname(nickname)
        .introduce(dto.getIntroduce())
        .gender(dto.getGender())
        .birth(dto.getBirth())
        .profile(profile)
        .build();
    Long id = accountUnit.create(entity);

    dto.getHashtagList().forEach(item -> {
      String hashtagId = item.getHashtag().getId();
      Hashtag hashtag = predefinedValidUnit.getHashtag(hashtagId);
      AccountHashtag accountHashtag = AccountHashtag
          .builder()
          .account(entity)
          .hashtag(hashtag)
          .build();

      accountHashtagUnit.create(accountHashtag);
    });

    rsaPairUnit.delete(encodePublicKey);

    LogAccountData after = LogAccountData
        .builder()
        .nickname(entity.getNickname())
        .introduce(entity.getIntroduce())
        .gender(entity.getGender())
        .birth(entity.getBirth())
        .profile(IdDocumentData
            .<Long>builder()
            .id(entity.getProfile().getId())
            .build())
        .hashtagList(entity
            .getHashtagList()
            .stream()
            .map(item -> IdDocumentData
                .<String>builder()
                .id(item.getId().getHashtagId())
                .build())
            .collect(Collectors.toSet()))
        .build();

    long now = now().toInstant(UTC).toEpochMilli();

    producer.publish(LOGGING_CREATE,
        KafkaData
            .builder()
            .event(LOGGING_ACCOUNT_INFO)
            .data(LogAccountBus
                .builder()
                .id(padding(cycleUnit.getCycleSequence(), 9) + now)
                .type(LogAccountType.JOIN)
                .after(after)
                .account(IdDocumentData
                    .<Long>builder()
                    .id(id)
                    .build())
                .scribe(IdDocumentData
                    .<Long>builder()
                    .id(id)
                    .build())
                .build())
            .build());

    return ok(buildId(id));
  }

  @Override
  public ResponseEntity<?> getAccountPage(AccountSearch search) {
    log.info("AccountService.getAccountPage");

    boolean isUser = !checkManager();

    if (isUser) {
      search.setStatus(ACTIVE);
    }

    CursorData<Account> entityPage = accountUnit.getPage(search);

    if (isUser) {
      CursorData<AccountListResponse> list = entityPage
          .map(item -> map(item, AccountListResponse.class));

      return ok(list);
    } else {
      CursorData<AccountListByManagerResponse> list = entityPage
          .map(item -> map(item, AccountListByManagerResponse.class));

      return ok(list);
    }
  }

  @Override
  public ResponseEntity<?> getAccount(Long id) {
    log.info("AccountService.getAccount");

    Boolean valid = accountUnit.exists(id);

    if (!valid) {
      return error(getExceptions().getEntityNotFound());
    }

    Account entity = accountUnit.get(id);

    valid = !entity.getDeleteFlag() && entity.getUseFlag() && entity.getStatus().equals(ACTIVE);

    boolean isUser = !checkManager();

    if (isUser && !valid) {
      return error(getExceptions().getEntityNotFound());
    }

    if (isUser) {
      AccountResponse data = map(entity, AccountResponse.class);

      setAccountStatistics(entity, data);

      return ok(data);
    } else {
      AccountByManagerResponse data = map(entity, AccountByManagerResponse.class);

      setAccountStatistics(entity, data);

      return ok(data);
    }
  }

  private <R extends AccountResponseExtension> void setAccountStatistics(Account entity, R data) {
    data.setFollowingCount(entity.getFollowingList().size());
    data.setFollowerCount(entity.getFollowerList().size());
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    _______   ______    __       __        ______   ____    __    ____
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |   ____| /  __  \  |  |     |  |      /  __  \  \   \  /  \  /   /
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__   |  |  |  | |  |     |  |     |  |  |  |  \   \/    \/   /
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __|  |  |  |  | |  |     |  |     |  |  |  |   \            /
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |     |  `--'  | |  `----.|  `----.|  `--'  |    \    /\    /
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|      \______/  |_______||_______| \______/      \__/  \__/
  @Override
  public ResponseEntity<?> getFollowingList() {
    log.info("AccountService.getFollowingList");

    Account signed = getSigned();

    List<AccountFollow> entityList = accountFollowUnit.getAccountFollowingList(signed);
    List<AccountFollowingListResponse> list = map(entityList, AccountFollowingListResponse.class);

    return ok(list);
  }

  @Override
  public ResponseEntity<?> getFollowerList() {
    log.info("AccountService.getFollowerList");

    Account signed = getSigned();

    List<AccountFollow> entityList = accountFollowUnit.getAccountFollowerList(signed);
    List<AccountFollowerListResponse> list = map(entityList, AccountFollowerListResponse.class);

    return ok(list);
  }

  @Override
  public ResponseEntity<?> existsFollow(Long id) {
    log.info("AccountService.existsFollow");

    Long signedId = getSignedId();

    if (Objects.equals(id, signedId)) {
      return error(getExceptions().getData());
    }

    AccountFollowId followId = AccountFollowId
        .builder()
        .followingId(id)
        .followerId(signedId)
        .build();

    Boolean exists = accountFollowUnit.exists(followId);

    return ok(IsResponse.builder().is(exists).build());
  }

  @Override
  @Transactional
  public ResponseEntity<?> toggleFollow(Long id) {
    log.info("AccountService.toggleFollow");

    Long signedId = getSignedId();

    if (Objects.equals(id, signedId)) {
      return error(getExceptions().getData());
    }

    AccountFollowId followId = AccountFollowId
        .builder()
        .followingId(id)
        .followerId(signedId)
        .build();

    Boolean exists = accountFollowUnit.exists(followId);

    if (exists) {
      accountFollowUnit.delete(followId);

      return ok();
    }

    boolean valid = accountUnit.validateAccount(id);

    if (!valid) {
      return error(getExceptions().getEntityNotFound());
    }

    Account following = accountUnit.get(id);

    AccountFollow entity = AccountFollow
        .builder()
        .following(following)
        .follower(getSigned())
        .build();

    accountFollowUnit.create(entity);

    return ok();
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|
  @Override
  public ResponseEntity<?> getNotificationPage(AccountSearch search) {
    log.info("AccountService.getNotificationPage");

    Account signed = getSigned();

    CursorData<AccountNotification> entityPage = accountNotificationUnit.getPage(search, signed);
    CursorData<AccountNotificationListResponse> list = entityPage
        .map(item -> map(item, AccountNotificationListResponse.class));

    return ok(list);
  }

  @Override
  public ResponseEntity<?> getNotificationByTypePage(AccountSearch search, String type) {
    log.info("AccountService.getNotificationPage");

    Account signed = getSigned();
    AccountNotificationInheritanceType division = AccountNotificationInheritanceType.find(type);

    CursorData<AccountNotification> entityPage = accountNotificationUnit.getPage(
        search, signed, division);
    CursorData<AccountNotificationListResponse> list = entityPage
        .map(item -> map(item, AccountNotificationListResponse.class));

    return ok(list);
  }

}
