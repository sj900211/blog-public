package run.freshr.domain.account.dto.data;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.enumerations.Gender;
import run.freshr.domain.predefined.dto.data.AttachData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSimpleData {

  private Long id;
  private String uuid;
  private String username;
  private String nickname;
  private Gender gender;
  private LocalDate birth;
  private AttachData profile;

}
