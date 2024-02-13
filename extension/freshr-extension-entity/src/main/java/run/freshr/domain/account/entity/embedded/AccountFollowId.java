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
public class AccountFollowId implements Serializable {

  private Long followingId;
  private Long followerId;

  @Builder
  public AccountFollowId(Long followingId, Long followerId) {
    log.info("AccountFollowId.Constructor");

    this.followingId = followingId;
    this.followerId = followerId;
  }

}
