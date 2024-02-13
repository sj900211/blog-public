package run.freshr.domain.logging.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogAccountData;
import run.freshr.domain.logging.enumerations.LogAccountType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogAccountBus {

  private String id;

  private LogAccountType type;

  private LogAccountData before;

  private LogAccountData after;

  private IdDocumentData<Long> account;

  private IdDocumentData<Long> scribe;

}
