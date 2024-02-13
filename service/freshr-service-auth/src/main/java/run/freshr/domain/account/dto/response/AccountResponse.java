package run.freshr.domain.account.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.response.ResponseLogicalExtension;
import run.freshr.domain.predefined.dto.data.AttachData;

/**
 * 사용자 계정 response DTO
 *
 * @author 류성재
 * @apiNote 사용자 계정 response DTO
 * @since 2023. 6. 16. 오후 4:47:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountResponse extends ResponseLogicalExtension<Long> {

  private String uuid;

  private String username;

  private String nickname;

  private String introduce;

  private Gender gender;

  private LocalDate birth;

  private LocalDateTime signAt;

  private AttachData profile;

  private Set<AccountHashtagFromAccountResponse> hashtagList;

}
