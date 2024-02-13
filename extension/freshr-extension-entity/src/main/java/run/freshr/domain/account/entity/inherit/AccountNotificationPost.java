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
import run.freshr.domain.blog.entity.Post;

@Slf4j
@Entity
@Table(schema = "account", name = "ACCOUNT_NOTIFICATION_POST")
@PrimaryKeyJoinColumn(foreignKey = @ForeignKey(name = "FPK_ACCOUNT_NOTIFICATION_POST"))
@Getter
@DynamicInsert
@DynamicUpdate
@DiscriminatorValue(Constant.POST)
@NoArgsConstructor(access = PROTECTED)
@Comment("사용자 관리 > 알림 관리 > 포스팅 알림 관리")
public class AccountNotificationPost extends AccountNotification {

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "origin_id",
      foreignKey = @ForeignKey(name = "FK_ACCOUNT_NOTIFICATION_POST_POST"))
  @Comment("대상 블로그 정보")
  private Post post;

  @Builder
  public AccountNotificationPost(String id, Account account,
      AccountNotificationType type, Post post) {
    log.info("AccountNotificationPost.Constructor");

    this.id = id;
    this.account = account;
    this.type = type;
    this.post = post;
  }

}
