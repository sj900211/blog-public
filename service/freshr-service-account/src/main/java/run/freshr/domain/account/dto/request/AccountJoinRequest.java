package run.freshr.domain.account.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.enumerations.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountJoinRequest {

  @NotEmpty
  private String rsa;

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

  @NotEmpty
  private String nickname;

  private String introduce;

  @NotNull
  private Gender gender;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  private IdRequest<Long> profile;

  private List<AccountHashtagFromAccountRequest> hashtagList;

}
