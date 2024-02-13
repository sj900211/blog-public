package run.freshr.service;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.utils.StringUtil.uuidWithoutHyphen;
import static run.freshr.common.utils.ThreadUtil.threadAccess;
import static run.freshr.common.utils.ThreadUtil.threadPublicKey;
import static run.freshr.common.utils.ThreadUtil.threadRefresh;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.enumerations.Reaction;
import run.freshr.common.enumerations.Visibility;
import run.freshr.common.mappers.EnumGetter;
import run.freshr.common.mappers.EnumMapper;
import run.freshr.common.security.TokenProvider;
import run.freshr.common.utils.CryptoUtil;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountFollowId;
import run.freshr.domain.account.entity.embedded.AccountHashtagId;
import run.freshr.domain.account.entity.inherit.AccountNotificationAccount;
import run.freshr.domain.account.entity.inherit.AccountNotificationBlog;
import run.freshr.domain.account.entity.inherit.AccountNotificationPost;
import run.freshr.domain.account.entity.inherit.AccountNotificationPostComment;
import run.freshr.domain.account.entity.mapping.AccountFollow;
import run.freshr.domain.account.entity.mapping.AccountHashtag;
import run.freshr.domain.account.enumerations.AccountNotificationType;
import run.freshr.domain.account.unit.jpa.AccountValidUnit;
import run.freshr.domain.auth.enumerations.Privilege;
import run.freshr.domain.auth.enumerations.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.unit.redis.AccessRedisUnit;
import run.freshr.domain.auth.unit.redis.RefreshRedisUnit;
import run.freshr.domain.auth.unit.redis.RsaPairUnit;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.entity.PostComment;
import run.freshr.domain.blog.entity.embedded.BlogHashtagId;
import run.freshr.domain.blog.entity.embedded.BlogParticipateId;
import run.freshr.domain.blog.entity.embedded.BlogParticipateRequestId;
import run.freshr.domain.blog.entity.embedded.BlogSubscribeId;
import run.freshr.domain.blog.entity.embedded.PostAttachId;
import run.freshr.domain.blog.entity.embedded.PostCommentReactionId;
import run.freshr.domain.blog.entity.embedded.PostCommentWardId;
import run.freshr.domain.blog.entity.embedded.PostHashtagId;
import run.freshr.domain.blog.entity.embedded.PostReactionId;
import run.freshr.domain.blog.entity.mapping.BlogHashtag;
import run.freshr.domain.blog.entity.mapping.BlogParticipate;
import run.freshr.domain.blog.entity.mapping.BlogParticipateRequest;
import run.freshr.domain.blog.entity.mapping.BlogSubscribe;
import run.freshr.domain.blog.entity.mapping.PostAttach;
import run.freshr.domain.blog.entity.mapping.PostCommentReaction;
import run.freshr.domain.blog.entity.mapping.PostCommentWard;
import run.freshr.domain.blog.entity.mapping.PostHashtag;
import run.freshr.domain.blog.entity.mapping.PostReaction;
import run.freshr.domain.blog.enumerations.BlogRole;
import run.freshr.domain.blog.unit.jpa.BlogValidUnit;
import run.freshr.domain.conversation.entity.Channel;
import run.freshr.domain.conversation.entity.embedded.ChannelAccountId;
import run.freshr.domain.conversation.entity.mapping.ChannelAccount;
import run.freshr.domain.conversation.enumerations.ChannelType;
import run.freshr.domain.conversation.unit.jpa.ConversationValidUnit;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.BasicImage;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.predefined.unit.jpa.PredefinedValidUnit;

/**
 * 테스트 데이터 관리 service 구현 class
 *
 * @author 류성재
 * @apiNote 테스트 데이터 관리 service 구현 class
 * @since 2023. 1. 13. 오전 11:35:07
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|
  private final EnumMapper enumMapper;

  @Override
  public Map<String, List<EnumGetter>> getEnumAll() {
    return enumMapper.getAll();
  }

  //      ___      __    __  .___________. __    __                     ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \    |  |  |  | |           ||  |  |  |      ___          /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |     ( _ )        /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |  |  |     |  |     |   __   |     / _ \/\     /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |    | (_>  <    /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______/      |__|     |__|  |__|     \___/\/   /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  private final RsaPairUnit rsaPairUnit;
  private final AccessRedisUnit authAccessUnit;
  private final RefreshRedisUnit authRefreshUnit;
  private final AccountValidUnit accountValidUnit;

  private final PasswordEncoder passwordEncoder;
  private final TokenProvider provider;

  @Override
  public void createRsa() {
    KeyPair keyPar = CryptoUtil.getKeyPar();
    PublicKey publicKey = keyPar.getPublic();
    PrivateKey privateKey = keyPar.getPrivate();
    String encodePublicKey = CryptoUtil.encodePublicKey(publicKey);
    String encodePrivateKey = CryptoUtil.encodePrivateKey(privateKey);

    threadPublicKey.set(encodePublicKey);

    RsaPair rsaPair = RsaPair
        .builder()
        .publicKey(encodePublicKey)
        .privateKey(encodePrivateKey)
        .createAt(LocalDateTime.now())
        .build();

    rsaPairUnit.save(rsaPair);
  }

  @Override
  public void createAuth(Long id, Role role) {
    threadAccess.remove();
    threadRefresh.remove();

    // 토큰 발급
    String accessToken = provider.generateAccessToken(id);
    String refreshToken = provider.generateRefreshToken(id);

    threadAccess.set(accessToken);
    threadRefresh.set(refreshToken);

    // 토큰 등록
    createAccess(accessToken, id, role);
    createRefresh(refreshToken, accessToken);
  }

  @Override
  public void createAccess(String accessToken, Long id, Role role) {
    AccessRedis accessRedis = AccessRedis
        .builder()
        .id(accessToken)
        .signId(id)
        .role(role)
        .build();

    authAccessUnit.save(accessRedis);
  }

  @Override
  public AccessRedis getAccess(String id) {
    return authAccessUnit.get(id);
  }

  @Override
  public void createRefresh(String refreshToken, String access) {
    RefreshRedis refreshRedis = RefreshRedis
        .builder()
        .id(refreshToken)
        .access(getAccess(access))
        .build();

    authRefreshUnit.save(refreshRedis);
  }

  @Override
  public RefreshRedis getRefresh(String refreshToken) {
    return authRefreshUnit.get(refreshToken);
  }

  @Override
  public long createAccount(String prefix, String suffix, Privilege privilege, Gender gender,
      Attach profile) {
    Account entity = Account
        .builder()
        .uuid(uuidWithoutHyphen())
        .privilege(privilege)
        .username(prefix + (hasLength(suffix) ? "-" : "") + suffix)
        .password(passwordEncoder.encode("1234"))
        .nickname(prefix + (hasLength(suffix) ? "-" : "") + suffix + " nickname")
        .introduce(prefix + (hasLength(suffix) ? "-" : "") + suffix + " introduce")
        .gender(gender)
        .birth(LocalDate.now().minusYears(19))
        .profile(profile)
        .build();

    return accountValidUnit.createAccount(entity);
  }

  public Account getAccount(long id) {
    return accountValidUnit.getAccount(id);
  }

  // .______   .______       _______  _______   _______  _______  __  .__   __.  _______  _______
  // |   _  \  |   _  \     |   ____||       \ |   ____||   ____||  | |  \ |  | |   ____||       \
  // |  |_)  | |  |_)  |    |  |__   |  .--.  ||  |__   |  |__   |  | |   \|  | |  |__   |  .--.  |
  // |   ___/  |      /     |   __|  |  |  |  ||   __|  |   __|  |  | |  . `  | |   __|  |  |  |  |
  // |  |      |  |\  \----.|  |____ |  '--'  ||  |____ |  |     |  | |  |\   | |  |____ |  '--'  |
  // | _|      | _| `._____||_______||_______/ |_______||__|     |__| |__| \__| |_______||_______/
  private final PredefinedValidUnit predefinedValidUnit;

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  @Override
  public long createAttach(String filename, String path) {
    Attach entity = Attach
        .builder()
        .contentType("image/png")
        .filename(filename)
        .key(path + "/" + filename)
        .size(2048L)
        .alt("alt")
        .title("title")
        .build();

    return predefinedValidUnit.createAttach(entity);
  }

  @Override
  public Attach getAttach(Long id) {
    return predefinedValidUnit.getAttach(id);
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  @Override
  public String createHashtag(String id) {
    Hashtag entity = Hashtag
        .builder()
        .id(id)
        .build();

    return predefinedValidUnit.createHashtag(entity);
  }

  @Override
  public Hashtag getHashtag(String id) {
    return predefinedValidUnit.getHashtag(id);
  }

  // .______        ___           _______. __    ______     __  .___  ___.      ___       _______  _______
  // |   _  \      /   \         /       ||  |  /      |   |  | |   \/   |     /   \     /  _____||   ____|
  // |  |_)  |    /  ^  \       |   (----`|  | |  ,----'   |  | |  \  /  |    /  ^  \   |  |  __  |  |__
  // |   _  <    /  /_\  \       \   \    |  | |  |        |  | |  |\/|  |   /  /_\  \  |  | |_ | |   __|
  // |  |_)  |  /  _____  \  .----)   |   |  | |  `----.   |  | |  |  |  |  /  _____  \ |  |__| | |  |____
  // |______/  /__/     \__\ |_______/    |__|  \______|   |__| |__|  |__| /__/     \__\ \______| |_______|
  @Override
  public long createBasicImage(Integer sort, BasicImageType type, Attach image) {
    BasicImage entity = BasicImage
        .builder()
        .sort(sort)
        .type(type)
        .image(image)
        .build();

    return predefinedValidUnit.createBasicImage(entity);
  }

  @Override
  public BasicImage getBasicImage(Long id) {
    return predefinedValidUnit.getBasicImage(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    _______   ______    __       __        ______   ____    __    ____
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |   ____| /  __  \  |  |     |  |      /  __  \  \   \  /  \  /   /
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__   |  |  |  | |  |     |  |     |  |  |  |  \   \/    \/   /
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __|  |  |  |  | |  |     |  |     |  |  |  |   \            /
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |     |  `--'  | |  `----.|  `----.|  `--'  |    \    /\    /
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|      \______/  |_______||_______| \______/      \__/  \__/
  @Override
  public AccountFollowId createAccountFollow(Account following, Account follower) {
    AccountFollow entity = AccountFollow
        .builder()
        .following(following)
        .follower(follower)
        .build();

    return accountValidUnit.createAccountFollow(entity);
  }

  @Override
  public AccountFollow getAccountFollow(AccountFollowId id) {
    return accountValidUnit.getAccountFollow(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    __    __       ___           _______. __    __  .___________.    ___       _______
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  @Override
  public AccountHashtagId createAccountHashtag(Account account, Hashtag hashtag) {
    AccountHashtag entity = AccountHashtag
        .builder()
        .account(account)
        .hashtag(hashtag)
        .build();

    return accountValidUnit.createAccountHashtag(entity);
  }

  @Override
  public AccountHashtag getAccountHashtag(AccountHashtagId id) {
    return accountValidUnit.getAccountHashtag(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.         ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |        /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |       /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |      /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |     /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  @Override
  public String createAccountNotificationAccount(String id, Account account,
      AccountNotificationType type, Account person) {
    AccountNotificationAccount entity = AccountNotificationAccount
        .builder()
        .id(id)
        .account(account)
        .type(type)
        .person(person)
        .build();

    return accountValidUnit.createAccountNotificationAccount(entity);
  }

  @Override
  public AccountNotificationAccount getAccountNotificationAccount(String id) {
    return accountValidUnit.getAccountNotificationAccount(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______    __        ______     _______
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \  |  |      /  __  \   /  _____|
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |     |  |  |  | |  |  __
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   _  <  |  |     |  |  |  | |  | |_ |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |_)  | |  `----.|  `--'  | |  |__| |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    |______/  |_______| \______/   \______|
  @Override
  public String createAccountNotificationBlog(String id, Account account,
      AccountNotificationType type, Blog blog) {
    AccountNotificationBlog entity = AccountNotificationBlog
        .builder()
        .id(id)
        .account(account)
        .type(type)
        .blog(blog)
        .build();

    return accountValidUnit.createAccountNotificationBlog(entity);
  }

  @Override
  public AccountNotificationBlog getAccountNotificationBlog(String id) {
    return accountValidUnit.getAccountNotificationBlog(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______     ______        _______.___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \   /  __  \      /       |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |  |  |    |   (----`---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   ___/  |  |  |  |     \   \       |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |      |  `--'  | .----)   |      |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    | _|       \______/  |_______/       |__|
  @Override
  public String createAccountNotificationPost(String id, Account account,
      AccountNotificationType type, Post post) {
    AccountNotificationPost entity = AccountNotificationPost
        .builder()
        .id(id)
        .account(account)
        .type(type)
        .post(post)
        .build();

    return accountValidUnit.createAccountNotificationPost(entity);
  }

  @Override
  public AccountNotificationPost getAccountNotificationPost(String id) {
    return accountValidUnit.getAccountNotificationPost(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|
  @Override
  public String createAccountNotificationPostComment(String id, Account account,
      AccountNotificationType type, PostComment postComment) {
    AccountNotificationPostComment entity = AccountNotificationPostComment
        .builder()
        .id(id)
        .account(account)
        .type(type)
        .postComment(postComment)
        .build();

    return accountValidUnit.createAccountNotificationPostComment(entity);
  }

  @Override
  public AccountNotificationPostComment getAccountNotificationPostComment(String id) {
    return accountValidUnit.getAccountNotificationPostComment(id);
  }

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|
  private final BlogValidUnit blogValidUnit;

  @Override
  public long createBlog(String key, String uuid, String title, String description,
      Visibility visibility, Boolean coverFlag, Attach cover, Channel channel) {
    Blog entity = Blog
        .builder()
        .key(key)
        .uuid(uuid)
        .title(title)
        .description(description)
        .visibility(visibility)
        .coverFlag(coverFlag)
        .cover(cover)
        .channel(channel)
        .build();

    return blogValidUnit.createBlog(entity);
  }

  @Override
  public Blog getBlog(Long id) {
    return blogValidUnit.getBlog(id);
  }

  // .______    __        ______     _______     __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \  |  |      /  __  \   /  _____|   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |______/  |_______| \______/   \______|    |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  @Override
  public BlogHashtagId createBlogHashtag(Blog blog, Hashtag hashtag) {
    BlogHashtag entity = BlogHashtag
        .builder()
        .blog(blog)
        .hashtag(hashtag)
        .build();

    return blogValidUnit.createBlogHashtag(entity);
  }

  @Override
  public BlogHashtag getBlogHashtag(BlogHashtagId id) {
    return blogValidUnit.getBlogHashtag(id);
  }

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______    .______       _______   ______      __    __   _______     _______.___________.
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|   |   _  \     |   ____| /  __  \    |  |  |  | |   ____|   /       |           |
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__      |  |_)  |    |  |__   |  |  |  |   |  |  |  | |  |__     |   (----`---|  |----`
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|     |      /     |   __|  |  |  |  |   |  |  |  | |   __|     \   \       |  |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____    |  |\  \----.|  |____ |  `--'  '--.|  `--'  | |  |____.----)   |      |  |
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|   | _| `._____||_______| \_____\_____\\______/  |_______|_______/       |__|
  @Override
  public BlogParticipateRequestId createBlogParticipateRequest(Blog blog, Account account) {
    BlogParticipateRequest entity = BlogParticipateRequest
        .builder()
        .blog(blog)
        .account(account)
        .build();

    return blogValidUnit.createBlogParticipateRequest(entity);
  }

  @Override
  public BlogParticipateRequest getBlogParticipateRequest(BlogParticipateRequestId id) {
    return blogValidUnit.getBlogParticipateRequest(id);
  }

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|
  @Override
  public BlogParticipateId createBlogParticipate(BlogRole role, Blog blog, Account account) {
    BlogParticipate entity = BlogParticipate
        .builder()
        .role(role)
        .blog(blog)
        .account(account)
        .build();

    return blogValidUnit.createBlogParticipate(entity);
  }

  @Override
  public BlogParticipate getBlogParticipate(BlogParticipateId id) {
    return blogValidUnit.getBlogParticipate(id);
  }

  // .______    __        ______     _______         _______. __    __  .______        _______.  ______ .______       __  .______    _______
  // |   _  \  |  |      /  __  \   /  _____|       /       ||  |  |  | |   _  \      /       | /      ||   _  \     |  | |   _  \  |   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __        |   (----`|  |  |  | |  |_)  |    |   (----`|  ,----'|  |_)  |    |  | |  |_)  | |  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |        \   \    |  |  |  | |   _  <      \   \    |  |     |      /     |  | |   _  <  |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    .----)   |   |  `--'  | |  |_)  | .----)   |   |  `----.|  |\  \----.|  | |  |_)  | |  |____
  // |______/  |_______| \______/   \______|    |_______/     \______/  |______/  |_______/     \______|| _| `._____||__| |______/  |_______|
  @Override
  public BlogSubscribeId createBlogSubscribe(Blog blog, Account account) {
    BlogSubscribe entity = BlogSubscribe
        .builder()
        .blog(blog)
        .account(account)
        .build();

    return blogValidUnit.createBlogSubscribe(entity);
  }

  @Override
  public BlogSubscribe getBlogSubscribe(BlogSubscribeId id) {
    return blogValidUnit.getBlogSubscribe(id);
  }

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  @Override
  public long createPost(String title, String contents, Visibility visibility, Blog blog) {
    Post entity = Post
        .builder()
        .title(title)
        .contents(contents)
        .visibility(visibility)
        .blog(blog)
        .build();

    return blogValidUnit.createPost(entity);
  }

  @Override
  public Post getPost(Long id) {
    return blogValidUnit.getPost(id);
  }

  // .______     ______        _______.___________.        ___   .___________.___________.    ___       __    __
  // |   _  \   /  __  \      /       |           |       /   \  |           |           |   /   \     |  |  |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`      /  ^  \ `---|  |----`---|  |----`  /  ^  \    |  |__|  |
  // |   ___/  |  |  |  |     \   \       |  |          /  /_\  \    |  |        |  |      /  /_\  \   |   __   |
  // |  |      |  `--'  | .----)   |      |  |         /  _____  \   |  |        |  |     /  _____  \  |  |  |  |
  // | _|       \______/  |_______/       |__|        /__/     \__\  |__|        |__|    /__/     \__\ |__|  |__|
  @Override
  public PostAttachId createPostAttach(Post post, Attach attach) {
    PostAttach entity = PostAttach
        .builder()
        .post(post)
        .attach(attach)
        .build();

    return blogValidUnit.createPostAttach(entity);
  }

  @Override
  public PostAttach getPostAttach(PostAttachId id) {
    return blogValidUnit.getPostAttach(id);
  }

  // .______     ______        _______.___________.    __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \   /  __  \      /       |           |   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   ___/  |  |  |  |     \   \       |  |        |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |      |  `--'  | .----)   |      |  |        |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // | _|       \______/  |_______/       |__|        |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  @Override
  public PostHashtagId createPostHashtag(Post post, Hashtag hashtag) {
    PostHashtag entity = PostHashtag
        .builder()
        .post(post)
        .hashtag(hashtag)
        .build();

    return blogValidUnit.createPostHashtag(entity);
  }

  @Override
  public PostHashtag getPostHashtag(PostHashtagId id) {
    return blogValidUnit.getPostHashtag(id);
  }

  // .______     ______        _______.___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  @Override
  public PostReactionId createPostReaction(Post post, Account account, Reaction reaction) {
    PostReaction entity = PostReaction
        .builder()
        .post(post)
        .account(account)
        .reaction(reaction)
        .build();

    return blogValidUnit.createPostReaction(entity);
  }

  @Override
  public PostReaction getPostReaction(PostReactionId id) {
    return blogValidUnit.getPostReaction(id);
  }

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|
  @Override
  public long createPostComment(String contents, Post post) {
    PostComment entity = PostComment
        .builder()
        .contents(contents)
        .post(post)
        .build();

    return blogValidUnit.createPostComment(entity);
  }

  @Override
  public PostComment getPostComment(Long id) {
    return blogValidUnit.getPostComment(id);
  }

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  @Override
  public PostCommentReactionId createPostCommentReaction(PostComment postComment, Account account,
      Reaction reaction) {
    PostCommentReaction entity = PostCommentReaction
        .builder()
        .postComment(postComment)
        .account(account)
        .reaction(reaction)
        .build();

    return blogValidUnit.createPostCommentReaction(entity);
  }

  @Override
  public PostCommentReaction getPostCommentReaction(PostCommentReactionId id) {
    return blogValidUnit.getPostCommentReaction(id);
  }

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   ____    __    ____  ___      .______       _______
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   \   \  /  \  /   / /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`    \   \/    \/   / /  ^  \    |  |_)  |    |  .--.  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |          \            / /  /_\  \   |      /     |  |  |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |           \    /\    / /  _____  \  |  |\  \----.|  '--'  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|            \__/  \__/ /__/     \__\ | _| `._____||_______/
  @Override
  public PostCommentWardId createPostCommentWard(Post post, Account account) {
    PostCommentWard entity = PostCommentWard
        .builder()
        .post(post)
        .account(account)
        .build();

    return blogValidUnit.createPostCommentWard(entity);
  }

  @Override
  public PostCommentWard getPostCommentWard(PostCommentWardId id) {
    return blogValidUnit.getPostCommentWard(id);
  }

  //   ______   ______   .__   __. ____    ____  _______ .______          _______.     ___   .___________. __    ______   .__   __.
  //  /      | /  __  \  |  \ |  | \   \  /   / |   ____||   _  \        /       |    /   \  |           ||  |  /  __  \  |  \ |  |
  // |  ,----'|  |  |  | |   \|  |  \   \/   /  |  |__   |  |_)  |      |   (----`   /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |
  // |  |     |  |  |  | |  . `  |   \      /   |   __|  |      /        \   \      /  /_\  \    |  |     |  | |  |  |  | |  . `  |
  // |  `----.|  `--'  | |  |\   |    \    /    |  |____ |  |\  \----.----)   |    /  _____  \   |  |     |  | |  `--'  | |  |\   |
  //  \______| \______/  |__| \__|     \__/     |_______|| _| `._____|_______/    /__/     \__\  |__|     |__|  \______/  |__| \__|
  private final ConversationValidUnit conversationValidUnit;

  //   ______  __    __       ___      .__   __. .__   __.  _______  __
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|
  @Override
  public long createChannel(ChannelType type, String uuid) {
    Channel entity = Channel
        .builder()
        .type(type)
        .uuid(uuid)
        .build();

    return conversationValidUnit.createChannel(entity);
  }

  @Override
  public Channel getChannel(Long id) {
    return conversationValidUnit.getChannel(id);
  }

  //   ______  __    __       ___      .__   __. .__   __.  _______  __              ___       ______   ______   ______    __    __  .__   __. .___________.
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |            /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |           /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |          /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.    /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|   /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  @Override
  public ChannelAccountId createChannelAccount(Channel channel, Account account) {
    ChannelAccount entity = ChannelAccount
        .builder()
        .channel(channel)
        .account(account)
        .build();

    return conversationValidUnit.createChannelAccount(entity);
  }

  @Override
  public ChannelAccount getChannelAccount(ChannelAccountId id) {
    return conversationValidUnit.getChannelAccount(id);
  }

}
