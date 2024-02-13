package run.freshr.domain.account.repository.jpa;

import java.util.List;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.mapping.AccountFollow;

public interface AccountFollowRepositoryCustom {

  List<AccountFollow> getFollowerList(Account following);

  List<AccountFollow> getFollowingList(Account follower);

}
