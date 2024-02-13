package run.freshr.domain.auth.dto.request;

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
import run.freshr.domain.account.dto.request.AccountHashtagFromAccountRequest;

/**
 * 내 정보 수정 request DTO
 *
 * @author 류성재
 * @apiNote 내 정보 수정 request DTO
 * @since 2023. 6. 16. 오후 4:46:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpdateRequest {

  /**
   * RSA 공개 키
   *
   * @apiNote RSA 공개 키
   * @since 2023. 6. 16. 오후 4:46:26
   */
  @NotEmpty
  private String rsa;

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
