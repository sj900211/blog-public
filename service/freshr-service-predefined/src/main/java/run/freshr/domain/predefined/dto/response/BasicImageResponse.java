package run.freshr.domain.predefined.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extensions.response.ResponseAuditLogicalExtension;
import run.freshr.domain.account.dto.data.AuditorData;
import run.freshr.domain.predefined.dto.data.AttachData;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BasicImageResponse extends ResponseAuditLogicalExtension<Long, AuditorData> {

  private Long id;

  private Integer sort;

  private AttachData image;

}
