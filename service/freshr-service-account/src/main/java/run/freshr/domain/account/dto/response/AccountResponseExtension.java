package run.freshr.domain.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extensions.response.ResponseLogicalExtension;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountResponseExtension extends ResponseLogicalExtension<Long> {

  protected Integer followingCount;
  protected Integer followerCount;

}
