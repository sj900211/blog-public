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
public class BlogParticipateRequestId implements Serializable {

  private Long blogId;
  private Long accountId;

  @Builder
  public BlogParticipateRequestId(Long blogId, Long accountId) {
    log.info("BlogAccountId.Constructor");

    this.blogId = blogId;
    this.accountId = accountId;
  }

}
