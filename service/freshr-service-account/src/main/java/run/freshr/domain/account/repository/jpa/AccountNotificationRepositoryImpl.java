package run.freshr.domain.account.repository.jpa;

import static java.util.Objects.isNull;
import static run.freshr.domain.account.entity.QAccount.account;
import static run.freshr.domain.account.entity.QAccountNotification.accountNotification;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationAccount.accountNotificationAccount;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationBlog.accountNotificationBlog;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationPost.accountNotificationPost;
import static run.freshr.domain.account.entity.inherit.QAccountNotificationPostComment.accountNotificationPostComment;
import static run.freshr.domain.blog.entity.QBlog.blog;
import static run.freshr.domain.blog.entity.QPost.post;
import static run.freshr.domain.blog.entity.QPostComment.postComment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import run.freshr.common.data.CursorData;
import run.freshr.common.utils.QueryUtilAware;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.AccountNotification;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.enumerations.AccountStatus;
import run.freshr.domain.account.vo.AccountSearch;
import run.freshr.domain.blog.entity.QBlog;
import run.freshr.domain.blog.entity.QPost;

@RequiredArgsConstructor
public class AccountNotificationRepositoryImpl implements AccountNotificationRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public CursorData<AccountNotification> getPage(AccountSearch search, Account entity,
      AccountNotificationInheritanceType division) {
    LocalDateTime cursorAt = search.getCursorAt();

    QBlog postBlog = new QBlog("post_notification_blog");
    QPost commentPost = new QPost("comment_notification_post");
    QBlog commentBlog = new QBlog("comment_notification_blog");

    BooleanBuilder booleanAll = new BooleanBuilder();
    BooleanBuilder booleanAccount = new BooleanBuilder();
    BooleanBuilder booleanBlog = new BooleanBuilder();
    BooleanBuilder booleanPost = new BooleanBuilder();
    BooleanBuilder booleanPostComment = new BooleanBuilder();

    booleanAccount.and(accountNotificationAccount.isNotNull())
        .and(account.isNotNull());

    booleanBlog.and(accountNotificationBlog.isNotNull())
        .and(blog.isNotNull());

    booleanPost.and(accountNotificationPost.isNotNull())
        .and(post.isNotNull())
        .and(postBlog.isNotNull());

    booleanPostComment.and(accountNotificationPostComment.isNotNull())
        .and(postComment.isNotNull())
        .and(commentPost.isNotNull())
        .and(commentBlog.isNotNull());

    booleanAll.or(booleanAccount)
        .or(booleanBlog)
        .or(booleanPost)
        .or(booleanPostComment);

    JPAQuery<AccountNotification> query = queryFactory.selectFrom(accountNotification)
        .where(accountNotification.account.eq(entity),
            accountNotification.createAt.before(cursorAt));

    if (isNull(division)) {
      query.leftJoin(accountNotificationAccount)
          .on(accountNotificationAccount.id.eq(accountNotification.id))
          .leftJoin(accountNotificationAccount.person, account)
          .on(account.status.eq(AccountStatus.ACTIVE),
              account.deleteFlag.isFalse(), account.useFlag.isTrue())

          .leftJoin(accountNotificationBlog)
          .on(accountNotificationBlog.id.eq(accountNotification.id))
          .leftJoin(accountNotificationBlog.blog, blog)
          .on(blog.deleteFlag.isFalse(), blog.useFlag.isTrue())

          .leftJoin(accountNotificationPost)
          .on(accountNotificationPost.id.eq(accountNotification.id))
          .leftJoin(accountNotificationPost.post, post)
          .on(post.deleteFlag.isFalse(), post.useFlag.isTrue())
          .leftJoin(post.blog, postBlog)
          .on(postBlog.deleteFlag.isFalse(), postBlog.useFlag.isTrue())

          .leftJoin(accountNotificationPostComment)
          .on(accountNotificationPostComment.id.eq(accountNotification.id))
          .leftJoin(accountNotificationPostComment.postComment, postComment)
          .on(postComment.deleteFlag.isFalse(), postComment.useFlag.isTrue())
          .leftJoin(postComment.post, commentPost)
          .on(commentPost.deleteFlag.isFalse(), commentPost.useFlag.isTrue())
          .leftJoin(commentPost.blog, commentBlog)
          .on(commentBlog.deleteFlag.isFalse(), commentBlog.useFlag.isTrue())
          .where(booleanAll);
    } else {
      switch (division) {
        case ACCOUNT -> query
            .innerJoin(accountNotificationAccount)
            .on(accountNotificationAccount.id.eq(accountNotification.id))
            .innerJoin(accountNotificationAccount.person, account)
            .on(account.status.eq(AccountStatus.ACTIVE),
                account.deleteFlag.isFalse(), account.useFlag.isTrue())
            .where(booleanAccount);

        case BLOG -> query
            .innerJoin(accountNotificationBlog)
            .on(accountNotificationBlog.id.eq(accountNotification.id))
            .innerJoin(accountNotificationBlog.blog, blog)
            .on(blog.deleteFlag.isFalse(), blog.useFlag.isTrue())
            .where(booleanBlog);

        case POST -> query
            .innerJoin(accountNotificationPost)
            .on(accountNotificationPost.id.eq(accountNotification.id))
            .innerJoin(accountNotificationPost.post, post)
            .on(post.deleteFlag.isFalse(), post.useFlag.isTrue())
            .innerJoin(post.blog, postBlog)
            .on(postBlog.deleteFlag.isFalse(), postBlog.useFlag.isTrue())
            .where(booleanPost);

        case POST_COMMENT -> query
            .innerJoin(accountNotificationPostComment)
            .on(accountNotificationPostComment.id.eq(accountNotification.id))
            .innerJoin(accountNotificationPostComment.postComment, postComment)
            .on(postComment.deleteFlag.isFalse(), postComment.useFlag.isTrue())
            .innerJoin(postComment.post, commentPost)
            .on(commentPost.deleteFlag.isFalse(), commentPost.useFlag.isTrue())
            .innerJoin(commentPost.blog, commentBlog)
            .on(commentBlog.deleteFlag.isFalse(), commentBlog.useFlag.isTrue())
            .where(booleanPostComment);
      }
    }

    return QueryUtilAware.paging(query, accountNotification, search,
        List.of(accountNotification.createAt.desc(), accountNotification.id.desc()));
  }

}
