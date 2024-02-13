package run.freshr.domain.logging.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogPostCommentData;
import run.freshr.domain.logging.enumerations.LogPostCommentType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogPostCommentBus {

  private String id;

  private LogPostCommentType type;

  private LogPostCommentData before;

  private LogPostCommentData after;

  private IdDocumentData<Long> post;

  private IdDocumentData<Long> postComment;

  private IdDocumentData<Long> scribe;

}
