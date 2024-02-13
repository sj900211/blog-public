package run.freshr.domain.logging.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogBlogData;
import run.freshr.domain.logging.enumerations.LogBlogType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogBlogBus {

  private String id;

  private LogBlogType type;

  private LogBlogData before;

  private LogBlogData after;

  private IdDocumentData<Long> blog;

  private IdDocumentData<Long> scribe;

}
