package run.freshr.domain.account.entity.embedded;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AccountHashtagId implements Serializable {

  private Long accountId;
  private String hashtagId;

  @Builder
  public AccountHashtagId(Long accountId, String hashtagId) {
    log.info("AccountHashtagId.Constructor");

    this.accountId = accountId;
    this.hashtagId = hashtagId;
  }

}
