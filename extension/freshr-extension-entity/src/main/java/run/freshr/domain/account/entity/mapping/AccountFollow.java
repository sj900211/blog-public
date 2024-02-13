package run.freshr.domain.account.entity.mapping;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extensions.entity.EntityPhysicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountFollowId;

@Slf4j
@Entity
@Table(
    schema = "account",
    name = "ACCOUNT_FOLLOW",
    indexes = {
        @Index(name = "IDX_ACCOUNT_FOLLOW_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("사용자 관리 > 팔로우 관리")
public class AccountFollow extends EntityPhysicalExtension {

  @EmbeddedId
  private AccountFollowId id;

  @MapsId("followingId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "following_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_ACCOUNT_FOLLOW_FOLLOWING"))
  @Comment("팔로잉 사용자 정보")
  private Account following;

  @MapsId("followerId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "follower_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_ACCOUNT_FOLLOW_FOLLOWER"))
  @Comment("팔로우 사용자 정보")
  private Account follower;

  @Builder
  public AccountFollow(Account following, Account follower) {
    log.info("Follow.Constructor");

    this.id = AccountFollowId
        .builder()
        .followingId(following.getId())
        .followerId(follower.getId())
        .build();
    this.following = following;
    this.follower = follower;
  }

}
