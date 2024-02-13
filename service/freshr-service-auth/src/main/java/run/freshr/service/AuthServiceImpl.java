package run.freshr.service;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static run.freshr.common.kafka.KafkaEvent.LOGGING_ACCOUNT_INFO;
import static run.freshr.common.kafka.KafkaTopic.LOGGING_CREATE;
import static run.freshr.common.utils.CryptoUtil.decryptRsa;
import static run.freshr.common.utils.CryptoUtil.encryptRsa;
import static run.freshr.common.utils.MapperUtil.map;
import static run.freshr.common.utils.RestUtil.error;
import static run.freshr.common.utils.RestUtil.getConfig;
import static run.freshr.common.utils.RestUtil.getCustomConfig;
import static run.freshr.common.utils.RestUtil.getExceptions;
import static run.freshr.common.utils.RestUtil.getSignedId;
import static run.freshr.common.utils.RestUtil.ok;
import static run.freshr.common.utils.RestUtilAuthAware.getSigned;
import static run.freshr.common.utils.RestUtilAware.checkProfile;
import static run.freshr.common.utils.StringUtil.padding;

import io.grpc.netty.shaded.io.netty.handler.codec.http.HttpScheme;
import jakarta.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import run.freshr.common.data.IdDocumentData;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.dto.response.KeyResponse;
import run.freshr.common.kafka.KafkaData;
import run.freshr.common.kafka.KafkaProducer;
import run.freshr.common.security.TokenProvider;
import run.freshr.common.utils.CryptoUtil;
import run.freshr.domain.account.dto.response.AccountResponse;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.mapping.AccountHashtag;
import run.freshr.domain.account.unit.jpa.AccountHashtagUnit;
import run.freshr.domain.account.unit.jpa.CycleUnit;
import run.freshr.domain.auth.dto.request.EncryptRequest;
import run.freshr.domain.auth.dto.request.RefreshTokenRequest;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import run.freshr.domain.auth.dto.response.EncryptResponse;
import run.freshr.domain.auth.dto.response.RefreshTokenResponse;
import run.freshr.domain.auth.dto.response.SignInResponse;
import run.freshr.domain.auth.enumerations.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.ChatAccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.unit.jpa.AccountAuthUnit;
import run.freshr.domain.auth.unit.redis.AccessRedisUnit;
import run.freshr.domain.auth.unit.redis.ChatAccessRedisUnit;
import run.freshr.domain.auth.unit.redis.RefreshRedisUnit;
import run.freshr.domain.auth.unit.redis.RsaPairUnit;
import run.freshr.domain.logging.bus.LogAccountBus;
import run.freshr.domain.logging.elasticsearch.data.LogAccountData;
import run.freshr.domain.logging.enumerations.LogAccountType;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.unit.jpa.PredefinedValidUnit;
import run.freshr.rocketchat.client.RocketChatUserClient;
import run.freshr.rocketchat.dto.request.RocketChatDeleteUserRequest;
import run.freshr.rocketchat.dto.request.RocketChatSetAvatarRequest;
import run.freshr.rocketchat.dto.request.RocketChatUpdateUserData;
import run.freshr.rocketchat.dto.request.RocketChatUpdateUserRequest;
import run.freshr.rocketchat.dto.response.RocketChatDeleteUserResponse;
import run.freshr.rocketchat.dto.response.RocketChatSetAvatarResponse;
import run.freshr.rocketchat.dto.response.RocketChatUpdateUserResponse;

/**
 * 권한 관리 service 구현 class
 *
 * @author 류성재
 * @apiNote 권한 관리 service 구현 class
 * @since 2023. 6. 16. 오후 4:54:40
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

  private final KafkaProducer producer;

  private final RocketChatUserClient rocketChatUserClient;

  private final AccountAuthUnit accountAuthUnit;

  private final CycleUnit cycleUnit;
  private final AccountHashtagUnit accountHashtagUnit;

  private final AccessRedisUnit authAccessUnit;
  private final RefreshRedisUnit authRefreshUnit;
  private final ChatAccessRedisUnit chatAccessRedisUnit;
  private final RsaPairUnit rsaPairUnit;

  private final TokenProvider provider;
  private final PasswordEncoder passwordEncoder;

  private final PredefinedValidUnit predefinedValidUnit;

  /**
   * RSA 공개키 조회
   *
   * @return public key
   * @apiNote RSA 공개키 조회
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  @Transactional
  public ResponseEntity<?> getPublicKey() {
    log.info("AuthService.getPublicKey");

    KeyPair keyPar = CryptoUtil.getKeyPar();
    PublicKey publicKey = keyPar.getPublic();
    PrivateKey privateKey = keyPar.getPrivate();
    String encodePublicKey = CryptoUtil.encodePublicKey(publicKey);
    String encodePrivateKey = CryptoUtil.encodePrivateKey(privateKey);

    // 생성한 RSA 정보를 Redis 에 저장
    RsaPair redis = RsaPair
        .builder()
        .publicKey(encodePublicKey)
        .privateKey(encodePrivateKey)
        .createAt(LocalDateTime.now())
        .build();

    rsaPairUnit.save(redis);

    return ok(KeyResponse.<String>builder().key(encodePublicKey).build());
  }

  /**
   * RSA 암호화 조회
   *
   * @param dto {@link EncryptRequest}
   * @return encrypt rsa
   * @apiNote RSA 암호화 조회<br> 사용하지 않는 것을 권장<br> RSA 암호화를 할 수 없는 플랫폼일 때 사용
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  public ResponseEntity<?> getEncryptRsa(EncryptRequest dto) {
    log.info("AuthService.getEncryptRsa");

    String encrypt = encryptRsa(dto.getPlain(), dto.getRsa());

    return ok(EncryptResponse.builder().encrypt(encrypt).build());
  }

  /**
   * 로그인
   *
   * @param dto {@link SignInRequest}
   * @return response entity
   * @apiNote 로그인
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  @Transactional
  public ResponseEntity<?> signIn(SignInRequest dto) {
    log.info("AuthService.signIn");

    String encodePublicKey = dto.getRsa();

    // RSA 유효 기간 체크
    if (!rsaPairUnit.checkRsa(encodePublicKey, getConfig().getRsaLimit().longValue())) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();
    String username = decryptRsa(dto.getUsername(), encodePrivateKey);

    // 요청 정보로 데이터가 있는지 체크
    if (!accountAuthUnit.exists(username)) {
      return error(getExceptions().getEntityNotFound());
    }

    Account entity = accountAuthUnit.get(username);

    // 탈퇴 여부 체크
    if (entity.getDeleteFlag()) {
      return error(getExceptions().getEntityNotFound());
    }

    // 활성 여부 체크
    if (!entity.getUseFlag()) {
      return error(getExceptions().getUnAuthenticated());
    }

    /// 비밀번호 체크
    if (!passwordEncoder.matches(decryptRsa(dto.getPassword(), encodePrivateKey),
        entity.getPassword())) {
      return error(getExceptions().getUnAuthenticated());
    }

    entity.signed();

    Long id = entity.getId();

    // 토큰 발급
    String accessToken = provider.generateAccessToken(id);
    String refreshToken = provider.generateRefreshToken(id);

    // 토큰 정보를 Redis 에 저장
    AccessRedis accessRedis = AccessRedis
        .builder()
        .id(accessToken)
        .signId(id)
        .role(entity.getPrivilege().getRole())
        .build();

    authAccessUnit.save(accessRedis);

    RefreshRedis refreshRedis = RefreshRedis
        .builder()
        .id(refreshToken)
        .access(authAccessUnit.get(accessToken))
        .build();

    authRefreshUnit.save(refreshRedis);

    SignInResponse response = SignInResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();

    rsaPairUnit.delete(encodePublicKey);

    long now = now().toInstant(UTC).toEpochMilli();

    producer.publish(LOGGING_CREATE,
        KafkaData
            .builder()
            .event(LOGGING_ACCOUNT_INFO)
            .data(LogAccountBus
                .builder()
                .id(padding(cycleUnit.getCycleSequence(), 9) + now)
                .type(LogAccountType.IN)
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

    return ok(response);
  }

  /**
   * 로그아웃
   *
   * @return response entity
   * @apiNote 로그아웃
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  @Transactional
  public ResponseEntity<?> signOut() {
    log.info("AuthService.signOut");

    Long signedId = getSignedId();

    AccessRedis accessRedis = authAccessUnit.get(signedId);

    authRefreshUnit.delete(accessRedis);
    authAccessUnit.delete(signedId);

    return ok();
  }

  /**
   * 내 정보 조회
   *
   * @return info
   * @apiNote 내 정보 조회
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  public ResponseEntity<?> getInfo() {
    log.info("AuthService.getInfo");

    return ok(map(getSigned(), AccountResponse.class));
  }

  /**
   * 비밀번호 변경
   *
   * @param dto {@link SignChangePasswordRequest}
   * @return response entity
   * @apiNote 비밀번호 변경
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  @Transactional
  public ResponseEntity<?> changePassword(SignChangePasswordRequest dto) {
    log.info("AuthService.changePassword");

    String encodePublicKey = dto.getRsa();

    // RSA 유효 기간 체크
    if (!rsaPairUnit.checkRsa(encodePublicKey, getConfig().getRsaLimit().longValue())) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();
    Long signedId = getSignedId();

    Account entity = accountAuthUnit.get(signedId);

    // 변경 전 비밀번호 체크
    if (!passwordEncoder.matches(decryptRsa(dto.getOriginPassword(), encodePrivateKey),
        entity.getPassword())) {
      return error(getExceptions().getUnAuthenticated());
    }

    String password = passwordEncoder.encode(decryptRsa(dto.getPassword(), encodePrivateKey));

    boolean matchesPrevious = passwordEncoder.matches(password, entity.getPreviousPassword());
    boolean matchesCurrent = passwordEncoder.matches(password, entity.getPassword());

    if (matchesPrevious) {
      return error(BAD_REQUEST,
          "Security Vulnerabilities",
          "E001",
          "This password is the password you were using before.");
    }

    if (matchesCurrent) {
      return error(BAD_REQUEST,
          "Security Vulnerabilities",
          "E002",
          "This password is exactly the same as the one you use now.");
    }

    entity.changePassword(password);

    if (!checkProfile("test")) {
      String authToken, userId;

      try {
        ChatAccessRedis managerChatAccess = chatAccessRedisUnit.getManager();

        authToken = managerChatAccess.getToken();
        userId = managerChatAccess.getId();
      } catch (HttpException e) {
        return error(getExceptions().getError(), e.getMessage());
      }

      ResponseEntity<RocketChatUpdateUserResponse> rocketChat = rocketChatUserClient.updateUser(
          authToken,
          userId,
          RocketChatUpdateUserRequest
              .builder()
              .userId(entity.getUuid())
              .data(RocketChatUpdateUserData
                  .builder()
                  .email(entity.getUsername())
                  .name(entity.getNickname())
                  .password(password)
                  .build())
              .build());
      HttpStatusCode rocketChatStatusCode = rocketChat.getStatusCode();

      if (rocketChatStatusCode.isError()) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      RocketChatUpdateUserResponse rocketChatBody = rocketChat.getBody();

      if (isNull(rocketChatBody)) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      Boolean rocketChatSuccess = rocketChatBody.getSuccess();

      if (rocketChatSuccess) {
        return error(getExceptions().getError(), rocketChat.toString());
      }
    }

    long now = now().toInstant(UTC).toEpochMilli();

    producer.publish(LOGGING_CREATE,
        KafkaData
            .builder()
            .event(LOGGING_ACCOUNT_INFO)
            .data(LogAccountBus
                .builder()
                .id(padding(cycleUnit.getCycleSequence(), 9) + now)
                .type(LogAccountType.PASSWORD)
                .account(IdDocumentData
                    .<Long>builder()
                    .id(signedId)
                    .build())
                .scribe(IdDocumentData
                    .<Long>builder()
                    .id(signedId)
                    .build())
                .build())
            .build());

    return ok();
  }

  /**
   * 내 정보 수정
   *
   * @param dto {@link SignUpdateRequest}
   * @return response entity
   * @apiNote 내 정보 수정
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  @Transactional
  public ResponseEntity<?> updateInfo(SignUpdateRequest dto) {
    log.info("AuthService.updateInfo");

    String encodePublicKey = dto.getRsa();

    // RSA 유효 기간 체크
    if (!rsaPairUnit.checkRsa(encodePublicKey, getConfig().getRsaLimit().longValue())) {
      return error(getExceptions().getAccessDenied());
    }

    RsaPair redis = rsaPairUnit.get(encodePublicKey);
    String encodePrivateKey = redis.getPrivateKey();
    Account signed = getSigned();
    Long signedId = signed.getId();

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

    LogAccountData before = LogAccountData
        .builder()
        .nickname(signed.getNickname())
        .introduce(signed.getIntroduce())
        .gender(signed.getGender())
        .birth(signed.getBirth())
        .profile(IdDocumentData
            .<Long>builder()
            .id(signed.getProfile().getId())
            .build())
        .hashtagList(signed
            .getHashtagList()
            .stream()
            .map(item -> IdDocumentData
                .<String>builder()
                .id(item.getId().getHashtagId())
                .build())
            .collect(Collectors.toSet()))
        .build();

    signed.updateEntity(decryptRsa(dto.getNickname(), encodePrivateKey), dto.getIntroduce(),
        dto.getGender(), dto.getBirth(), profile);

    accountHashtagUnit.deleteAll(signed);

    dto.getHashtagList().forEach(item -> {
      String hashtagId = item.getHashtag().getId();
      Hashtag hashtag = predefinedValidUnit.getHashtag(hashtagId);
      AccountHashtag entity = AccountHashtag
          .builder()
          .account(signed)
          .hashtag(hashtag)
          .build();

      accountHashtagUnit.create(entity);
    });

    if (!checkProfile("test")) {
      String rocketChatUserId = signed.getUuid();
      String authToken, userId;

      try {
        ChatAccessRedis managerChatAccess = chatAccessRedisUnit.getManager();

        authToken = managerChatAccess.getToken();
        userId = managerChatAccess.getId();
      } catch (HttpException e) {
        return error(getExceptions().getError(), e.getMessage());
      }

      ResponseEntity<RocketChatUpdateUserResponse> rocketChatUpdate = rocketChatUserClient
          .updateUser(
              authToken,
              userId,
              RocketChatUpdateUserRequest
                  .builder()
                  .userId(rocketChatUserId)
                  .data(RocketChatUpdateUserData
                      .builder()
                      .email(signed.getUsername())
                      .name(signed.getNickname())
                      .password(signed.getPassword())
                      .build())
                  .build());
      HttpStatusCode rocketChatUpdateStatusCode = rocketChatUpdate.getStatusCode();

      if (rocketChatUpdateStatusCode.isError()) {
        return error(getExceptions().getError(), rocketChatUpdate.toString());
      }

      RocketChatUpdateUserResponse rocketChatUpdateBody = rocketChatUpdate.getBody();

      if (isNull(rocketChatUpdateBody)) {
        return error(getExceptions().getError(), rocketChatUpdate.toString());
      }

      Boolean rocketChatUpdateSuccess = rocketChatUpdateBody.getSuccess();

      if (rocketChatUpdateSuccess) {
        return error(getExceptions().getError(), rocketChatUpdate.toString());
      }

      if (!isNull(profile)) {
        ResponseEntity<RocketChatSetAvatarResponse> rocketChatAvatar = rocketChatUserClient.setAvatar(
            authToken,
            userId,
            RocketChatSetAvatarRequest
                .builder()
                .userId(rocketChatUserId)
                .avatarUrl(UriComponentsBuilder
                    .newInstance()
                    .scheme(HttpScheme.HTTPS.name().toString())
                    .host(getCustomConfig().getGateway())
                    .path("/predefined/attach/" + profile.getId())
                    .toUriString())
                .build());
        HttpStatusCode rocketChatAvatarStatusCode = rocketChatAvatar.getStatusCode();

        if (rocketChatAvatarStatusCode.isError()) {
          return error(getExceptions().getError(), rocketChatAvatar.toString());
        }

        RocketChatSetAvatarResponse rocketChatAvatarBody = rocketChatAvatar.getBody();

        if (isNull(rocketChatAvatarBody)) {
          return error(getExceptions().getError(), rocketChatAvatar.toString());
        }

        Boolean rocketChatAvatarSuccess = rocketChatAvatarBody.getSuccess();

        if (rocketChatAvatarSuccess) {
          return error(getExceptions().getError(), rocketChatAvatar.toString());
        }
      }
    }

    LogAccountData after = LogAccountData
        .builder()
        .nickname(signed.getNickname())
        .introduce(signed.getIntroduce())
        .gender(signed.getGender())
        .birth(signed.getBirth())
        .profile(IdDocumentData
            .<Long>builder()
            .id(signed.getProfile().getId())
            .build())
        .hashtagList(signed
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
                .type(LogAccountType.UPDATE)
                .before(before)
                .after(after)
                .account(IdDocumentData
                    .<Long>builder()
                    .id(signedId)
                    .build())
                .scribe(IdDocumentData
                    .<Long>builder()
                    .id(signedId)
                    .build())
                .build())
            .build());

    return ok();
  }

  /**
   * 탈퇴
   *
   * @return response entity
   * @apiNote 탈퇴
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  @Transactional
  public ResponseEntity<?> withdrawal() {
    log.info("AuthService.withdrawal");

    Account signed = getSigned();
    Long id = signed.getId();

    signed.withdrawal();

    authRefreshUnit.delete(authAccessUnit.get(id));
    authAccessUnit.delete(id);

    if (!checkProfile("test")) {
      String authToken, userId;

      try {
        ChatAccessRedis managerChatAccess = chatAccessRedisUnit.getManager();

        authToken = managerChatAccess.getToken();
        userId = managerChatAccess.getId();
      } catch (HttpException e) {
        return error(getExceptions().getError(), e.getMessage());
      }

      ResponseEntity<RocketChatDeleteUserResponse> rocketChat = rocketChatUserClient.deleteUser(
          authToken,
          userId,
          RocketChatDeleteUserRequest
              .builder()
              .userId(signed.getUuid())
              .build());
      HttpStatusCode rocketChatStatusCode = rocketChat.getStatusCode();

      if (rocketChatStatusCode.isError()) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      RocketChatDeleteUserResponse rocketChatBody = rocketChat.getBody();

      if (isNull(rocketChatBody)) {
        return error(getExceptions().getError(), rocketChat.toString());
      }

      Boolean rocketChatSuccess = rocketChatBody.getSuccess();

      if (rocketChatSuccess) {
        return error(getExceptions().getError(), rocketChat.toString());
      }
    }

    long now = now().toInstant(UTC).toEpochMilli();

    LogAccountData before = LogAccountData
        .builder()
        .nickname(signed.getNickname())
        .introduce(signed.getIntroduce())
        .gender(signed.getGender())
        .birth(signed.getBirth())
        .profile(IdDocumentData
            .<Long>builder()
            .id(signed.getProfile().getId())
            .build())
        .hashtagList(signed
            .getHashtagList()
            .stream()
            .map(item -> IdDocumentData
                .<String>builder()
                .id(item.getId().getHashtagId())
                .build())
            .collect(Collectors.toSet()))
        .build();

    producer.publish(LOGGING_CREATE,
        KafkaData
            .builder()
            .event(LOGGING_ACCOUNT_INFO)
            .data(LogAccountBus
                .builder()
                .id(padding(cycleUnit.getCycleSequence(), 9) + now)
                .type(LogAccountType.WITHDRAWAL)
                .before(before)
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

    return ok();
  }

  /**
   * Access 토큰 갱신
   *
   * @param request 요청 정보
   * @param dto     {@link RefreshTokenRequest}
   * @return response entity
   * @apiNote Access 토큰 갱신
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:54:40
   */
  @Override
  @Transactional
  public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, RefreshTokenRequest dto) {
    log.info("AuthService.refreshToken");

    String refreshToken = provider.resolve(request);
    String accessToken = dto.getAccessToken();

    provider.validateRefresh(refreshToken);

    RefreshRedis refresh = authRefreshUnit.get(refreshToken); // Refresh Token 상세 조회

    /*
     * Refresh 토큰과 pairing 된 Access 토큰 정보와 요청 Access 토큰 정보가 다른지 체크
     * 두 정보가 다르다면 요청 Refresh 토큰은 제 3 자에게 탈취당한 것으로 판단
     * Refresh 토큰과 모든 Access 토큰을 파기
     * 모든 디바이스에서 로그아웃 처리
     */
    if (!refresh.getAccess().getId().equals(accessToken)) {
      authAccessUnit.delete(accessToken);
      authAccessUnit.delete(refresh.getAccess().getId());
      authRefreshUnit.delete(refreshToken);

      return error(getExceptions().getUnAuthenticated());
    }

    LocalDateTime updateAt = refresh.getUpdateAt(); // Access Token 갱신 날짜 시간 조회
    AccessRedis access = authAccessUnit.get(accessToken); // Access Token 상세 조회
    Long id = access.getSignId(); // 계정 일련 번호 조회
    Role role = access.getRole(); // 계정 권한 조회

    // 인증인가 유지 기간을 넘었는지 확인. 넘었다면 로그아웃 처리
    long limit = getConfig().getAuthLimit(); // 60 일

    if (Duration.between(updateAt, LocalDateTime.now()).getSeconds() > limit) {
      authAccessUnit.delete(accessToken);
      authRefreshUnit.delete(refreshToken);

      return error(getExceptions().getUnAuthenticated());
    }

    // 새로운 Access Token 발급
    String newAccessToken = provider.generateAccessToken(id);
    AccessRedis newAccessRedis = AccessRedis
        .builder()
        .id(newAccessToken)
        .signId(id)
        .role(role)
        .build();

    authAccessUnit.delete(accessToken);
    authAccessUnit.save(newAccessRedis);

    refresh.updateRedis(authAccessUnit.get(newAccessToken));
    authRefreshUnit.save(refresh);

    // 계정 최근 접속 날짜 시간 갱신
    accountAuthUnit.get(id).signed();

    RefreshTokenResponse response = RefreshTokenResponse
        .builder()
        .accessToken(newAccessToken)
        .build();

    return ok(response);
  }

}
