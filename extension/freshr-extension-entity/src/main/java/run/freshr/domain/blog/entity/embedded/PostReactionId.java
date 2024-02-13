package run.freshr.domain.blog.entity.embedded;

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
public class PostReactionId implements Serializable {

  private Long postId;
  private Long accountId;

  @Builder
  public PostReactionId(Long postId, Long accountId) {
    log.info("PostReactionId.Constructor");

    this.postId = postId;
    this.accountId = accountId;
  }

}
