package run.freshr.domain.blog.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.enumerations.Visibility;
import run.freshr.domain.predefined.dto.data.AttachData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogSimpleData {

  private Long id;
  private String key;
  private String uuid;
  private String title;
  private Visibility visibility;
  private Boolean coverFlag;
  private AttachData cover;

}
