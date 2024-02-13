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
public class PostHashtagId implements Serializable {

  private Long postId;
  private String hashtagId;

  @Builder
  public PostHashtagId(Long postId, String hashtagId) {
    log.info("PostHashtagId.Constructor");

    this.postId = postId;
    this.hashtagId = hashtagId;
  }

}
