package run.freshr.service;

import org.springframework.boot.ApplicationRunner;
import run.freshr.common.enumerations.Reaction;
import run.freshr.common.enumerations.Visibility;
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
import run.freshr.domain.conversation.entity.Channel;
import run.freshr.domain.conversation.entity.embedded.ChannelAccountId;
import run.freshr.domain.conversation.entity.mapping.ChannelAccount;
import run.freshr.domain.conversation.enumerations.ChannelType;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.BasicImage;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.statistic.elasticsearch.StatisticHashtag;

/**
 * 테스트 데이터 관리 service 공통 기능
 *
 * @author 류성재
 * @apiNote {@link ApplicationRunner} 를 상속받은 test runner 에서<br>
 *          데이터 설정을 쉽게하기 위해서 {@link TestServiceAware} 를 상속받아 데이터 생성 기능을 재정의<br>
 *          필수 작성은 아니며, 테스트 코드에서 데이터 생성 기능을 조금이라도 더 편하게 사용하고자 만든 Service<br>
 *          권한과 같은 특수한 경우를 제외한 대부분은 데이터에 대한 Create, Get 정도만 작성해서 사용을 한다.
 * @since 2023. 1. 13. 오전 11:35:07
 */
public interface TestService extends TestServiceAware {

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  long createAttach(String filename, String path);

  Attach getAttach(Long id);

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  String createHashtag(String id);

  Hashtag getHashtag(String id);

  void createStatisticHashtag(String id, long accountCount, long blogCount, long postCount);

  StatisticHashtag getStatisticHashtag(String id);

  // .______        ___           _______. __    ______     __  .___  ___.      ___       _______  _______
  // |   _  \      /   \         /       ||  |  /      |   |  | |   \/   |     /   \     /  _____||   ____|
  // |  |_)  |    /  ^  \       |   (----`|  | |  ,----'   |  | |  \  /  |    /  ^  \   |  |  __  |  |__
  // |   _  <    /  /_\  \       \   \    |  | |  |        |  | |  |\/|  |   /  /_\  \  |  | |_ | |   __|
  // |  |_)  |  /  _____  \  .----)   |   |  | |  `----.   |  | |  |  |  |  /  _____  \ |  |__| | |  |____
  // |______/  /__/     \__\ |_______/    |__|  \______|   |__| |__|  |__| /__/     \__\ \______| |_______|
  long createBasicImage(Integer sort, BasicImageType type, Attach image);

  BasicImage getBasicImage(Long id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    _______   ______    __       __        ______   ____    __    ____
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |   ____| /  __  \  |  |     |  |      /  __  \  \   \  /  \  /   /
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__   |  |  |  | |  |     |  |     |  |  |  |  \   \/    \/   /
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __|  |  |  |  | |  |     |  |     |  |  |  |   \            /
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |     |  `--'  | |  `----.|  `----.|  `--'  |    \    /\    /
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|      \______/  |_______||_______| \______/      \__/  \__/
  AccountFollowId createAccountFollow(Account following, Account follower);

  AccountFollow getAccountFollow(AccountFollowId id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    __    __       ___           _______. __    __  .___________.    ___       _______
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  AccountHashtagId createAccountHashtag(Account account, Hashtag hashtag);

  AccountHashtag getAccountHashtag(AccountHashtagId id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.         ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |        /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |       /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |      /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |     /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  String createAccountNotificationAccount(String id, Account account, AccountNotificationType type,
      Account origin);

  AccountNotificationAccount getAccountNotificationAccount(String id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______    __        ______     _______
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \  |  |      /  __  \   /  _____|
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |     |  |  |  | |  |  __
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   _  <  |  |     |  |  |  | |  | |_ |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |_)  | |  `----.|  `--'  | |  |__| |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    |______/  |_______| \______/   \______|
  String createAccountNotificationBlog(String id, Account account, AccountNotificationType type,
      Blog origin);

  AccountNotificationBlog getAccountNotificationBlog(String id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______     ______        _______.___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \   /  __  \      /       |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |  |  |    |   (----`---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   ___/  |  |  |  |     \   \       |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |      |  `--'  | .----)   |      |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    | _|       \______/  |_______/       |__|
  String createAccountNotificationPost(String id, Account account, AccountNotificationType type,
      Post origin);

  AccountNotificationPost getAccountNotificationPost(String id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|
  String createAccountNotificationPostComment(String id, Account account,
      AccountNotificationType type, PostComment origin);

  AccountNotificationPostComment getAccountNotificationPostComment(String id);

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|
  long createBlog(String key, String uuid, String title, String description, Visibility visibility,
      Boolean coverFlag, Attach cover, Channel channel);

  Blog getBlog(Long id);

  // .______    __        ______     _______     __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \  |  |      /  __  \   /  _____|   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |______/  |_______| \______/   \______|    |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  BlogHashtagId createBlogHashtag(Blog blog, Hashtag hashtag);

  BlogHashtag getBlogHashtag(BlogHashtagId id);

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______    .______       _______   ______      __    __   _______     _______.___________.
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|   |   _  \     |   ____| /  __  \    |  |  |  | |   ____|   /       |           |
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__      |  |_)  |    |  |__   |  |  |  |   |  |  |  | |  |__     |   (----`---|  |----`
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|     |      /     |   __|  |  |  |  |   |  |  |  | |   __|     \   \       |  |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____    |  |\  \----.|  |____ |  `--'  '--.|  `--'  | |  |____.----)   |      |  |
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|   | _| `._____||_______| \_____\_____\\______/  |_______|_______/       |__|
  BlogParticipateRequestId createBlogParticipateRequest(Blog blog, Account account);

  BlogParticipateRequest getBlogParticipateRequest(BlogParticipateRequestId id);

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|
  BlogParticipateId createBlogParticipate(BlogRole role, Blog blog, Account account);

  BlogParticipate getBlogParticipate(BlogParticipateId id);

  // .______    __        ______     _______         _______. __    __  .______        _______.  ______ .______       __  .______    _______
  // |   _  \  |  |      /  __  \   /  _____|       /       ||  |  |  | |   _  \      /       | /      ||   _  \     |  | |   _  \  |   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __        |   (----`|  |  |  | |  |_)  |    |   (----`|  ,----'|  |_)  |    |  | |  |_)  | |  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |        \   \    |  |  |  | |   _  <      \   \    |  |     |      /     |  | |   _  <  |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    .----)   |   |  `--'  | |  |_)  | .----)   |   |  `----.|  |\  \----.|  | |  |_)  | |  |____
  // |______/  |_______| \______/   \______|    |_______/     \______/  |______/  |_______/     \______|| _| `._____||__| |______/  |_______|
  BlogSubscribeId createBlogSubscribe(Blog blog, Account account);

  BlogSubscribe getBlogSubscribe(BlogSubscribeId id);

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  long createPost(String title, String contents, Visibility visibility, Blog blog);

  Post getPost(Long id);

  // .______     ______        _______.___________.        ___   .___________.___________.    ___       __    __
  // |   _  \   /  __  \      /       |           |       /   \  |           |           |   /   \     |  |  |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`      /  ^  \ `---|  |----`---|  |----`  /  ^  \    |  |__|  |
  // |   ___/  |  |  |  |     \   \       |  |          /  /_\  \    |  |        |  |      /  /_\  \   |   __   |
  // |  |      |  `--'  | .----)   |      |  |         /  _____  \   |  |        |  |     /  _____  \  |  |  |  |
  // | _|       \______/  |_______/       |__|        /__/     \__\  |__|        |__|    /__/     \__\ |__|  |__|
  PostAttachId createPostAttach(Post post, Attach attach);

  PostAttach getPostAttach(PostAttachId id);

  // .______     ______        _______.___________.    __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \   /  __  \      /       |           |   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   ___/  |  |  |  |     \   \       |  |        |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |      |  `--'  | .----)   |      |  |        |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // | _|       \______/  |_______/       |__|        |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  PostHashtagId createPostHashtag(Post post, Hashtag hashtag);

  PostHashtag getPostHashtag(PostHashtagId id);

  // .______     ______        _______.___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  PostReactionId createPostReaction(Post post, Account account, Reaction reaction);

  PostReaction getPostReaction(PostReactionId id);

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|
  long createPostComment(String contents, Post post);

  PostComment getPostComment(Long id);

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  PostCommentReactionId createPostCommentReaction(PostComment postComment, Account account,
      Reaction reaction);

  PostCommentReaction getPostCommentReaction(PostCommentReactionId id);

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   ____    __    ____  ___      .______       _______
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   \   \  /  \  /   / /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`    \   \/    \/   / /  ^  \    |  |_)  |    |  .--.  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |          \            / /  /_\  \   |      /     |  |  |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |           \    /\    / /  _____  \  |  |\  \----.|  '--'  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|            \__/  \__/ /__/     \__\ | _| `._____||_______/
  PostCommentWardId createPostCommentWard(Post post, Account account);

  PostCommentWard getPostCommentWard(PostCommentWardId id);

  //   ______  __    __       ___      .__   __. .__   __.  _______  __
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|
  long createChannel(ChannelType type, String uuid);

  Channel getChannel(Long id);

  //   ______  __    __       ___      .__   __. .__   __.  _______  __              ___       ______   ______   ______    __    __  .__   __. .___________.
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |            /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |           /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |          /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.    /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|   /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  ChannelAccountId createChannelAccount(Channel channel, Account account);

  ChannelAccount getChannelAccount(ChannelAccountId id);

}
