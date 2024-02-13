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
import run.freshr.domain.blog.entity.PostComment;

@Slf4j
@Entity
@Table(schema = "account", name = "ACCOUNT_NOTIFICATION_POST_COMMENT")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FPK_ACCOUNT_NOTIFICATION_POST_COMMENT"))
@Getter
@DynamicInsert
@DynamicUpdate
@DiscriminatorValue(Constant.POST_COMMENT)
@NoArgsConstructor(access = PROTECTED)
@Comment("사용자 관리 > 알림 관리 > 블로그 알림 관리")
public class AccountNotificationPostComment extends AccountNotification {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "origin_id",
      foreignKey = @ForeignKey(name = "FK_ACCOUNT_NOTIFICATION_POST_COMMENT_POST_COMMENT"))
  @Comment("대상 블로그 정보")
  protected PostComment postComment;

  @Builder
  public AccountNotificationPostComment(String id, Account account,
      AccountNotificationType type, PostComment postComment) {
    log.info("AccountNotificationPostComment.Constructor");

    this.id = id;
    this.account = account;
    this.type = type;
    this.postComment = postComment;
  }

}
