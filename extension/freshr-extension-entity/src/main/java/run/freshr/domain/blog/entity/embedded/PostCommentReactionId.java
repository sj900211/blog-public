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
public class PostCommentReactionId implements Serializable {

  private Long postCommentId;
  private Long accountId;

  @Builder
  public PostCommentReactionId(Long postCommentId, Long accountId) {
    log.info("PostCommentReactionId.Constructor");

    this.postCommentId = postCommentId;
    this.accountId = accountId;
  }

}
