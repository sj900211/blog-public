package run.freshr.domain.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.domain.account.dto.data.AccountSimpleData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountFollowerListResponse {

  private AccountSimpleData follower;

}
