package run.freshr.domain.logging.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogPostData;
import run.freshr.domain.logging.enumerations.LogPostType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogPostBus {

  private String id;

  private LogPostType type;

  private LogPostData before;

  private LogPostData after;

  private IdDocumentData<Long> blog;

  private IdDocumentData<Long> post;

  private IdDocumentData<Long> scribe;

}
