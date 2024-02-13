package run.freshr.domain.account.unit.jpa;

import java.util.List;
import run.freshr.common.extensions.unit.UnitDefaultExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountFollowId;
import run.freshr.domain.account.entity.mapping.AccountFollow;

public interface AccountFollowUnit extends UnitDefaultExtension<AccountFollow, AccountFollowId> {

  void delete(AccountFollowId id);
  List<AccountFollow> getAccountFollowerList(Account following);
  List<AccountFollow> getAccountFollowingList(Account follower);

}
