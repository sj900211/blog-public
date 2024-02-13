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
public class BlogHashtagId implements Serializable {

  private Long blogId;
  private String hashtagId;

  @Builder
  public BlogHashtagId(Long blogId, String hashtagId) {
    log.info("BlogHashtagId.Constructor");

    this.blogId = blogId;
    this.hashtagId = hashtagId;
  }

}
