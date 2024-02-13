package run.freshr.domain.account.entity.inherit;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.AccountNotification;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType.Constant;
import run.freshr.domain.account.enumerations.AccountNotificationType;
import run.freshr.domain.blog.entity.Blog;

@Slf4j
@Entity
@Table(schema = "account", name = "ACCOUNT_NOTIFICATION_BLOG")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FPK_ACCOUNT_NOTIFICATION_BLOG"))
@Getter
@DynamicInsert
@DynamicUpdate
@DiscriminatorValue(Constant.BLOG)
@NoArgsConstructor(access = PROTECTED)
@Comment("사용자 관리 > 알림 관리 > 블로그 알림 관리")
public class AccountNotificationBlog extends AccountNotification {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "origin_id",
      foreignKey = @ForeignKey(name = "FK_ACCOUNT_NOTIFICATION_BLOG_BLOG"))
  @Comment("대상 블로그 정보")
  private Blog blog;

  @Builder
  public AccountNotificationBlog(String id, Account account,
      AccountNotificationType type, Blog blog) {
    log.info("AccountNotificationBlog.Constructor");

    this.id = id;
    this.account = account;
    this.type = type;
    this.blog = blog;
  }

}
