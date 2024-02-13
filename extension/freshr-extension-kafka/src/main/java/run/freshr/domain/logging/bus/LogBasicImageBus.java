package run.freshr.domain.logging.bus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogBasicImageData;
import run.freshr.domain.logging.enumerations.LogBasicImageType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogBasicImageBus {

  private String id;

  private LogBasicImageType type;

  private LogBasicImageData before;

  private LogBasicImageData after;

  private IdDocumentData<Long> basicImage;

  private IdDocumentData<Long> scribe;

}
