package run.freshr.domain.blog.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.data.IdLongData;
import run.freshr.common.enumerations.Visibility;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSimpleData {

  private Long id;
  private String title;
  private String contents;
  private Visibility visibility;
  private IdLongData blog;

}
