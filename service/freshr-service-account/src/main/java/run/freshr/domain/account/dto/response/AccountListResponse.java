package run.freshr.domain.account.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.response.ResponseLogicalExtension;
import run.freshr.domain.predefined.dto.data.AttachData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountListResponse extends ResponseLogicalExtension<Long> {

  private String uuid;
  private String username;
  private String nickname;
  private Gender gender;
  private LocalDate birth;
  private LocalDateTime signAt;
  private AttachData profile;

}
