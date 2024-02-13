package run.freshr.domain.account.unit.jpa;

import run.freshr.common.extensions.unit.UnitPageExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.vo.AccountSearch;

public interface AccountUnit extends UnitPageExtension<Account, Long, AccountSearch> {

  Boolean existsByUsername(String username);
  Boolean existsByNickname(String nickname);
  Account get(String username);
  boolean validateAccount(Long id);

}
