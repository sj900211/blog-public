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
import run.freshr.domain.account.entity.embedded.AccountHashtagId;
import run.freshr.domain.predefined.entity.Hashtag;

@Slf4j
@Entity
@Table(
    schema = "account",
    name = "ACCOUNT_HASHTAG",
    indexes = {
        @Index(name = "IDX_ACCOUNT_HASHTAG_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("사용자 관리 > 해시태그 관리")
public class AccountHashtag extends EntityPhysicalExtension {

  @EmbeddedId
  private AccountHashtagId id;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_ACCOUNT_HASHTAG_ACCOUNT"))
  @Comment("사용자 정보")
  private Account account;

  @MapsId("hashtagId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "hashtag_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_ACCOUNT_HASHTAG_HASHTAG"))
  @Comment("해시태그 정보")
  private Hashtag hashtag;

  @Builder
  public AccountHashtag(Account account, Hashtag hashtag) {
    log.info("AccountHashtag.Constructor");

    this.id = AccountHashtagId
        .builder()
        .accountId(account.getId())
        .hashtagId(hashtag.getId())
        .build();
    this.account = account;
    this.hashtag = hashtag;
  }

}
