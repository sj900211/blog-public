package run.freshr.domain.account;

import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static run.freshr.domain.account.entity.QAccountNotification.accountNotification;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationAccount.accountNotificationAccount;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationBlog.accountNotificationBlog;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationPost.accountNotificationPost;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationPostComment.accountNotificationPostComment;
import static run.freshr.domain.blog.entity.QPost.post;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.DataDocs;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;
import run.freshr.domain.account.vo.BAccountSearch;
import run.freshr.domain.blog.entity.QPost;

@Slf4j
public class AccountNotificationDocs {

  public static class Request {
    public static List<ParameterDescriptor> getNotificationPage() {
      log.info("AccountNotificationDocs.getNotificationPage");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.page, BAccountSearch.size)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAccountNotificationByTypePagePath() {
      log.info("AccountNotificationDocs.getAccountNotificationByTypePagePath");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .linkParameter("account-notification-division",
              "type", "조회 유형")

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAccountNotificationByTypePage() {
      log.info("AccountNotificationDocs.getAccountNotificationByTypePage");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.page, BAccountSearch.size)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBlogNotificationByTypePagePath() {
      log.info("AccountNotificationDocs.getBlogNotificationByTypePagePath");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .linkParameter("account-notification-division",
              "type", "조회 유형")

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getBlogNotificationByTypePage() {
      log.info("AccountNotificationDocs.getBlogNotificationByTypePage");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.page, BAccountSearch.size)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPostNotificationByTypePagePath() {
      log.info("AccountNotificationDocs.getPostNotificationByTypePagePath");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .linkParameter("account-notification-division",
              "type", "조회 유형")

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPostNotificationByTypePage() {
      log.info("AccountNotificationDocs.getPostNotificationByTypePage");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.page, BAccountSearch.size)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPostCommentNotificationByTypePagePath() {
      log.info("AccountNotificationDocs.getPostCommentNotificationByTypePagePath");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .linkParameter("account-notification-division",
              "type", "조회 유형")

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getPostCommentNotificationByTypePage() {
      log.info("AccountNotificationDocs.getPostCommentNotificationByTypePage");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.page, BAccountSearch.size)

          .build()
          .getParameterList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> getNotificationPage() {
      log.info("AccountNotificationDocs.getNotificationPage");

      return ResponseDocs
          .cursor()

          .prefixDescription("알림")

          .field(accountNotification.id, accountNotification.read, accountNotification.createAt)

          .linkField("account-notification-division", accountNotification.division)
          .linkField("account-notification-type", accountNotification.type)

          .prefixOptional()

          .prefixDescription("알림 사용자")

          .field(accountNotificationAccount.person, "정보", OBJECT, true)
          .field(
              accountNotificationAccount.person.id,
              accountNotificationAccount.person.uuid,
              accountNotificationAccount.person.username,
              accountNotificationAccount.person.nickname,
              accountNotificationAccount.person.birth
          )
          .linkField("account-gender", accountNotificationAccount.person.gender)
          .prefixDescription("알림 사용자 프로필 이미지")
          .field(accountNotificationAccount.person.profile, "정보", OBJECT, true)
          .addField(DataDocs.Predefined.attachData("page.content[].person.profile",
              "알림 사용자 프로필 이미지", true))

          .prefixDescription("알림 블로그")

          .field(accountNotificationBlog.blog, "정보", OBJECT, true)
          .field(
              accountNotificationBlog.blog.id,
              accountNotificationBlog.blog.key,
              accountNotificationBlog.blog.uuid,
              accountNotificationBlog.blog.title,
              accountNotificationBlog.blog.coverFlag
          )
          .linkField("common-visibility", accountNotificationBlog.blog.visibility)
          .prefixDescription("알림 블로그 커버 이미지")
          .field(accountNotificationBlog.blog.cover, "정보", OBJECT, true)
          .addField(DataDocs.Predefined.attachData("page.content[].blog.cover",
              "알림 블로그 커버 이미지", true))

          .prefixDescription("알림 포스팅")

          .field(accountNotificationPost.post, "정보", OBJECT, true)
          .field(
              accountNotificationPost.post.id,
              accountNotificationPost.post.title,
              accountNotificationPost.post.contents,
              accountNotificationPost.post.blog
          )
          .linkField("common-visibility", accountNotificationPost.post.visibility)
          .prefixDescription("알림 포스팅 블로그")
          .field(accountNotificationPost.post.blog, "정보", OBJECT, true)
          .field(accountNotificationPost.post.blog.id)

          .prefixDescription("알림 포스팅 댓글")

          .field(accountNotificationPostComment.postComment, "정보", OBJECT, true)
          .field(accountNotificationPostComment.postComment.id)
          .prefixDescription("알림 포스팅 댓글 포스팅")
          .field(accountNotificationPostComment.postComment.post, "정보", OBJECT, true)
          .field(
              accountNotificationPostComment.postComment.post.id,
              accountNotificationPostComment.postComment.post.title,
              accountNotificationPostComment.postComment.post.contents
          )
          .linkField("common-visibility",
              accountNotificationPostComment.postComment.post.visibility)
          .prefixDescription("알림 포스팅 댓글 포스팅 블로그")
          .prefix("page.content[].postComment.post")
          .field(post.blog, "정보", OBJECT, true)
          .field(post.blog.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccountNotificationByTypePage() {
      log.info("AccountNotificationDocs.getAccountNotificationByTypePage");

      return ResponseDocs
          .cursor()

          .prefixDescription("알림")

          .field(accountNotification.id, accountNotification.read, accountNotification.createAt)

          .linkField("account-notification-division", accountNotification.division)
          .linkField("account-notification-type", accountNotification.type)

          .prefixDescription("알림 사용자")

          .field(accountNotificationAccount.person, "정보", OBJECT)
          .field(
              accountNotificationAccount.person.id,
              accountNotificationAccount.person.uuid,
              accountNotificationAccount.person.username,
              accountNotificationAccount.person.nickname,
              accountNotificationAccount.person.birth
          )
          .linkField("account-gender", accountNotificationAccount.person.gender)
          .prefixDescription("알림 사용자 프로필 이미지")
          .field(accountNotificationAccount.person.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("page.content[].person.profile",
              "알림 사용자 프로필 이미지", true))

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getBlogNotificationByTypePage() {
      log.info("AccountNotificationDocs.getBlogNotificationByTypePage");

      return ResponseDocs
          .cursor()

          .prefixDescription("알림")

          .field(accountNotification.id, accountNotification.read, accountNotification.createAt)

          .linkField("account-notification-division", accountNotification.division)
          .linkField("account-notification-type", accountNotification.type)

          .prefixDescription("알림 블로그")

          .field(accountNotificationBlog.blog, "정보", OBJECT)
          .field(
              accountNotificationBlog.blog.id,
              accountNotificationBlog.blog.key,
              accountNotificationBlog.blog.uuid,
              accountNotificationBlog.blog.title,
              accountNotificationBlog.blog.coverFlag
          )
          .linkField("common-visibility", accountNotificationBlog.blog.visibility)
          .prefixDescription("알림 블로그 커버 이미지")
          .field(accountNotificationBlog.blog.cover, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("page.content[].blog.cover",
              "알림 블로그 커버 이미지", true))

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getPostNotificationByTypePage() {
      log.info("AccountNotificationDocs.getPostNotificationByTypePage");

      return ResponseDocs
          .cursor()

          .prefixDescription("알림")

          .field(accountNotification.id, accountNotification.read, accountNotification.createAt)

          .linkField("account-notification-division", accountNotification.division)
          .linkField("account-notification-type", accountNotification.type)

          .prefixDescription("알림 포스팅")

          .field(accountNotificationPost.post, "정보", OBJECT)
          .field(
              accountNotificationPost.post.id,
              accountNotificationPost.post.title,
              accountNotificationPost.post.contents
          )
          .linkField("common-visibility", accountNotificationPost.post.visibility)
          .prefixDescription("알림 포스팅 블로그")
          .field(accountNotificationPost.post.blog, "정보", OBJECT)
          .field(accountNotificationPost.post.blog.id)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getPostCommentNotificationByTypePage() {
      log.info("AccountNotificationDocs.getPostCommentNotificationByTypePage");

      return ResponseDocs
          .cursor()

          .prefixDescription("알림")

          .field(accountNotification.id, accountNotification.read, accountNotification.createAt)

          .linkField("account-notification-division", accountNotification.division)
          .linkField("account-notification-type", accountNotification.type)

          .prefixDescription("알림 포스팅 댓글")

          .field(accountNotificationPostComment.postComment, "정보", OBJECT)
          .field(accountNotificationPostComment.postComment.id)
          .prefixDescription("알림 포스팅 댓글 포스팅")
          .field(accountNotificationPostComment.postComment.post, "정보", OBJECT)
          .field(
              accountNotificationPostComment.postComment.post.id,
              accountNotificationPostComment.postComment.post.title,
              accountNotificationPostComment.postComment.post.contents
          )
          .linkField("common-visibility",
              accountNotificationPostComment.postComment.post.visibility)
          .prefixDescription("알림 포스팅 댓글 포스팅 블로그")
          .prefix("page.content[].postComment.post")
          .field(post.blog, "정보", OBJECT)
          .field(post.blog.id)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
