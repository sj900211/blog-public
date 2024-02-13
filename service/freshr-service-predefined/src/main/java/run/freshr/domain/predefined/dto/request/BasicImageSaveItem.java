package run.freshr.domain.predefined.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.request.IdRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicImageSaveItem {

  @NotNull
  private Integer sort;

  @NotNull
  private IdRequest<Long> image;

}
