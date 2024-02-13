package run.freshr.domain.account.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.predefined.dto.data.AttachData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditorData {

  private Long id;

  private String username;

  private String nickname;

  private AttachData profile;

}
