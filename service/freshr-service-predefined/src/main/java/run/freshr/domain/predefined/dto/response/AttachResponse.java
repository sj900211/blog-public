package run.freshr.domain.predefined.dto.response;

import run.freshr.common.extensions.response.ResponseLogicalExtension;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttachResponse extends ResponseLogicalExtension<Long> {

  private String contentType;

  private String filename;

  private URL url;

  private Long size;

  private String alt;

  private String title;

}
