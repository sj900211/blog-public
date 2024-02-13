package run.freshr.domain.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.response.IdResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHashtagFromAccountResponse {

  private IdResponse<String> hashtag;

}
