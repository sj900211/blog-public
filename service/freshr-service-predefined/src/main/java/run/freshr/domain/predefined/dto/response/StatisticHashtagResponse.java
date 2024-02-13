package run.freshr.domain.predefined.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticHashtagResponse {

  private String id;

  private Long accountCount;

  private Long blogCount;

  private Long postCount;

}
