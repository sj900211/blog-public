package run.freshr.domain.account.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.request.IdRequest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHashtagFromAccountRequest {

  private IdRequest<String> hashtag;

}
