package run.freshr.domain.predefined.dto.data;

import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachData {

  private Long id;

  private String contentType;

  private String filename;

  private URL url;

  private Long size;

  private String alt;

  private String title;

}
