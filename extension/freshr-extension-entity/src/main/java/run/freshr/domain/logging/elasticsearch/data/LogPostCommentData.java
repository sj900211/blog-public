package run.freshr.domain.logging.elasticsearch.data;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LogPostCommentData {

  private String contents;

  @Builder
  public LogPostCommentData(String contents) {
    this.contents = contents;
  }

}
