package run.freshr.domain.account.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.enumerations.Gender;
import run.freshr.domain.predefined.dto.data.AttachData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountResponse extends AccountResponseExtension {

  private String uuid;
  private String username;
  private String nickname;
  private Gender gender;
  private LocalDate birth;
  private String introduce;
  private LocalDateTime signAt;
  private AttachData profile;
  private List<AccountHashtagFromAccountResponse> hashtagList;

}
