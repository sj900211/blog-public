package run.freshr.domain.logging.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogChannelData;
import run.freshr.domain.logging.enumerations.LogChannelType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogChannelBus {

  private String id;

  private LogChannelType type;

  private LogChannelData before;

  private LogChannelData after;

  private IdDocumentData<Long> channel;

  private IdDocumentData<Long> scribe;

}
