package run.freshr.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.TestRunner.attachIdList;
import static run.freshr.TestRunner.hashtagIdList;
import static run.freshr.TestRunner.userIdList;
import static run.freshr.common.configurations.URIConfig.uriAccount;
import static run.freshr.common.configurations.URIConfig.uriAccountExistsNickname;
import static run.freshr.common.configurations.URIConfig.uriAccountExistsUsername;
import static run.freshr.common.configurations.URIConfig.uriAccountFollower;
import static run.freshr.common.configurations.URIConfig.uriAccountFollowing;
import static run.freshr.common.configurations.URIConfig.uriAccountId;
import static run.freshr.common.configurations.URIConfig.uriAccountIdFollow;
import static run.freshr.common.configurations.URIConfig.uriAccountIdFollowExists;
import static run.freshr.common.configurations.URIConfig.uriAccountJoin;
import static run.freshr.common.configurations.URIConfig.uriAccountNotification;
import static run.freshr.common.configurations.URIConfig.uriAccountNotificationType;
import static run.freshr.common.utils.CryptoUtil.encryptRsa;
import static run.freshr.common.utils.ThreadUtil.threadPublicKey;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import run.freshr.common.annotations.Docs;
import run.freshr.common.annotations.DocsGroup;
import run.freshr.common.annotations.DocsPopup;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.TestExtension;
import run.freshr.domain.account.AccountDocs;
import run.freshr.domain.account.AccountFollowDocs;
import run.freshr.domain.account.AccountNotificationDocs;
import run.freshr.domain.account.dto.request.AccountHashtagFromAccountRequest;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.vo.AccountSearch;

@Slf4j
@DisplayName("사용자 계정 관리")
@DocsGroup(name = "account")
public class AccountControllerTest extends TestExtension {

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  @Test
  @DisplayName("고유 아이디 중복 확인")
  @Docs(existsQueryParameters = true, existsResponseFields = true)
  public void existsAccountByUsername() throws Exception {
    log.info("AccountControllerTest.existsAccountByUsername");

    setAnonymous();

    AccountSearch search = new AccountSearch();

    search.setWord("input-username");

    GET_PARAM(uriAccountExistsUsername, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(AccountDocs.Request.existsAccountByUsername()),
            responseFields(AccountDocs.Response.existsAccountByUsername())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("닉네임 중복 확인")
  @Docs(existsQueryParameters = true, existsResponseFields = true)
  public void existsAccountByNickname() throws Exception {
    log.info("AccountControllerTest.existsAccountByNickname");

    setAnonymous();

    AccountSearch search = new AccountSearch();

    search.setWord("input-nickname");

    GET_PARAM(uriAccountExistsNickname, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(AccountDocs.Request.existsAccountByNickname()),
            responseFields(AccountDocs.Response.existsAccountByNickname())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 가입")
  @Docs(existsRequestFields = true, existsResponseFields = true)
  public void join() throws Exception {
    log.info("AccountControllerTest.join");

    setAnonymous();
    setRsa();

    String rsa = threadPublicKey.get();

    POST_BODY(
        uriAccountJoin,
        AccountJoinRequest
            .builder()
            .rsa(rsa)
            .username(encryptRsa("input-username", rsa))
            .password(encryptRsa("input-password", rsa))
            .nickname(encryptRsa("input-nickname", rsa))
            .introduce("input introduce message")
            .gender(Gender.OTHERS)
            .birth(LocalDate.now().minusYears(14))
            .profile(IdRequest.<Long>builder().id(attachIdList.get(0)).build())
            .hashtagList(List.of(
                AccountHashtagFromAccountRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder().id(hashtagIdList.get(0)).build())
                    .build(),
                AccountHashtagFromAccountRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder().id(hashtagIdList.get(1)).build())
                    .build(),
                AccountHashtagFromAccountRequest
                    .builder()
                    .hashtag(IdRequest.<String>builder().id(hashtagIdList.get(2)).build())
                    .build()
            ))
            .build()
    ).andDo(print())
        .andDo(docs(
            requestFields(AccountDocs.Request.join()),
            responseFields(AccountDocs.Response.join())
        )).andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 조회 - Page by User & Anonymous")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-search-key", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-search-keys.adoc"),
      @DocsPopup(name = "account-gender", include =
          "common-controller-test/get-enum-list/popup/popup-fields-gender.adoc")
  })
  public void getAccountPage() throws Exception {
    log.info("AccountControllerTest.getAccountPage");

    setSignedUser();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(uriAccount, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(AccountDocs.Request.getAccountPage()),
            responseFields(AccountDocs.Response.getAccountPage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 조회 - Page by Manager")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-search-key", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-search-keys.adoc"),
      @DocsPopup(name = "account-status", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-status.adoc"),
      @DocsPopup(name = "account-privilege", include =
          "common-controller-test/get-enum-list/popup/popup-fields-privilege.adoc"),
      @DocsPopup(name = "account-gender", include =
          "common-controller-test/get-enum-list/popup/popup-fields-gender.adoc")
  })
  public void getAccountPageByManager() throws Exception {
    log.info("AccountControllerTest.getAccountPageByManager");

    setSignedManagerMajor();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(uriAccount, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(AccountDocs.Request.getAccountPageByManager()),
            responseFields(AccountDocs.Response.getAccountPageByManager())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 조회 by User & Anonymous")
  @Docs(existsPathParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-gender", include =
          "common-controller-test/get-enum-list/popup/popup-fields-gender.adoc")
  })
  public void getAccountByManager() throws Exception {
    log.info("AccountControllerTest.getAccountByManager");

    setSignedUser();

    GET(uriAccountId, userIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(AccountDocs.Request.getAccount()),
            responseFields(AccountDocs.Response.getAccount())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 조회 by Manager")
  @Docs(existsPathParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-status", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-status.adoc"),
      @DocsPopup(name = "account-privilege", include =
          "common-controller-test/get-enum-list/popup/popup-fields-privilege.adoc"),
      @DocsPopup(name = "account-gender", include =
          "common-controller-test/get-enum-list/popup/popup-fields-gender.adoc")
  })
  public void getAccount() throws Exception {
    log.info("AccountControllerTest.getAccount");

    setSignedManagerMajor();

    GET(uriAccountId, userIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(AccountDocs.Request.getAccountByManager()),
            responseFields(AccountDocs.Response.getAccountByManager())
        ))
        .andExpect(status().isOk());
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    _______   ______    __       __        ______   ____    __    ____
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |   ____| /  __  \  |  |     |  |      /  __  \  \   \  /  \  /   /
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__   |  |  |  | |  |     |  |     |  |  |  |  \   \/    \/   /
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __|  |  |  |  | |  |     |  |     |  |  |  |   \            /
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |     |  `--'  | |  `----.|  `----.|  `--'  |    \    /\    /
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|      \______/  |_______||_______| \______/      \__/  \__/
  @Test
  @DisplayName("팔로잉 목록 조회")
  @Docs(existsResponseFields = true)
  public void getFollowingList() throws Exception {
    log.info("AccountControllerTest.getFollowingList");

    setSignedUser();

    GET(uriAccountFollowing)
        .andDo(print())
        .andDo(docs(responseFields(AccountFollowDocs.Response.getFollowingList())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("팔로워 목록 조회")
  @Docs(existsResponseFields = true)
  public void getFollower() throws Exception {
    log.info("AccountControllerTest.getFollowerList");

    setSignedUser();

    GET(uriAccountFollower)
        .andDo(print())
        .andDo(docs(responseFields(AccountFollowDocs.Response.getFollowerList())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("팔로우 여부 확인")
  @Docs(existsPathParameters = true, existsResponseFields = true)
  public void existsFollow() throws Exception {
    log.info("AccountControllerTest.existsFollow");

    setSignedUser();

    GET(uriAccountIdFollowExists, userIdList.get(1))
        .andDo(print())
        .andDo(docs(
            pathParameters(AccountFollowDocs.Request.existsFollow()),
            responseFields(AccountFollowDocs.Response.existsFollow())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("팔로우 & 팔로우 취소 toggle")
  @Docs(existsPathParameters = true)
  public void toggleFollow() throws Exception {
    log.info("AccountControllerTest.toggleFollow");

    setSignedUser();

    POST(uriAccountIdFollow, userIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AccountFollowDocs.Request.toggleFollow())))
        .andExpect(status().isOk());
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|
  @Test
  @DisplayName("알림 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-notification-division", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-inheritance-type.adoc"),
      @DocsPopup(name = "account-notification-type", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-type.adoc"),
      @DocsPopup(name = "account-gender", include =
          "common-controller-test/get-enum-list/popup/popup-fields-gender.adoc"),
      @DocsPopup(name = "common-visibility", include =
          "common-controller-test/get-enum-list/popup/popup-fields-visibility.adoc")
  })
  public void getNotificationPage() throws Exception {
    log.info("AccountControllerTest.getNotificationPage");

    setSignedUser();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(uriAccountNotification, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(AccountNotificationDocs.Request.getNotificationPage()),
            responseFields(AccountNotificationDocs.Response.getNotificationPage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("사용자 알림 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-notification-division", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-inheritance-type.adoc"),
      @DocsPopup(name = "account-notification-type", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-type.adoc"),
      @DocsPopup(name = "account-gender", include =
          "common-controller-test/get-enum-list/popup/popup-fields-gender.adoc")
  })
  public void getAccountNotificationByTypePage() throws Exception {
    log.info("AccountControllerTest.getAccountNotificationByTypePage");

    setSignedUser();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(
        uriAccountNotificationType,
        search,
        AccountNotificationInheritanceType.ACCOUNT.name().toLowerCase()
    ).andDo(print())
        .andDo(docs(
            pathParameters(AccountNotificationDocs.Request.getAccountNotificationByTypePagePath()),
            queryParameters(AccountNotificationDocs.Request.getAccountNotificationByTypePage()),
            responseFields(AccountNotificationDocs.Response.getAccountNotificationByTypePage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("블로그 알림 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-notification-division", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-inheritance-type.adoc"),
      @DocsPopup(name = "account-notification-type", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-type.adoc"),
      @DocsPopup(name = "common-visibility", include =
          "common-controller-test/get-enum-list/popup/popup-fields-visibility.adoc")
  })
  public void getBlogNotificationByTypePage() throws Exception {
    log.info("AccountControllerTest.getBlogNotificationByTypePage");

    setSignedUser();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(
        uriAccountNotificationType,
        search,
        AccountNotificationInheritanceType.BLOG.name().toLowerCase()
    ).andDo(print())
        .andDo(docs(
            pathParameters(AccountNotificationDocs.Request.getBlogNotificationByTypePagePath()),
            queryParameters(AccountNotificationDocs.Request.getBlogNotificationByTypePage()),
            responseFields(AccountNotificationDocs.Response.getBlogNotificationByTypePage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 알림 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-notification-division", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-inheritance-type.adoc"),
      @DocsPopup(name = "account-notification-type", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-type.adoc"),
      @DocsPopup(name = "common-visibility", include =
          "common-controller-test/get-enum-list/popup/popup-fields-visibility.adoc")
  })
  public void getPostNotificationByTypePage() throws Exception {
    log.info("AccountControllerTest.getPostNotificationByTypePage");

    setSignedUser();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(
        uriAccountNotificationType,
        search,
        AccountNotificationInheritanceType.POST.name().toLowerCase()
    ).andDo(print())
        .andDo(docs(
            pathParameters(AccountNotificationDocs.Request.getPostNotificationByTypePagePath()),
            queryParameters(AccountNotificationDocs.Request.getPostNotificationByTypePage()),
            responseFields(AccountNotificationDocs.Response.getPostNotificationByTypePage())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("포스팅 댓글 알림 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true, popup = {
      @DocsPopup(name = "account-notification-division", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-inheritance-type.adoc"),
      @DocsPopup(name = "account-notification-type", include =
          "common-controller-test/get-enum-list/popup/popup-fields-account-notification-type.adoc"),
      @DocsPopup(name = "common-visibility", include =
          "common-controller-test/get-enum-list/popup/popup-fields-visibility.adoc")
  })
  public void getPostCommentNotificationByTypePage() throws Exception {
    log.info("AccountControllerTest.getPostCommentNotificationByTypePage");

    setSignedUser();

    AccountSearch search = new AccountSearch();

    search.setPage(2);
    search.setSize(5);

    GET_PARAM(
        uriAccountNotificationType,
        search,
        AccountNotificationInheritanceType.POST_COMMENT.name().toLowerCase()
    ).andDo(print())
        .andDo(docs(
            pathParameters(
                AccountNotificationDocs.Request.getPostCommentNotificationByTypePagePath()),
            queryParameters(AccountNotificationDocs.Request.getPostCommentNotificationByTypePage()),
            responseFields(AccountNotificationDocs.Response.getPostCommentNotificationByTypePage())
        ))
        .andExpect(status().isOk());
  }

}
