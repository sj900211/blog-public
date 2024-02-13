package run.freshr.domain.account.unit.jpa;

import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountHashtagId;
import run.freshr.domain.account.entity.mapping.AccountHashtag;

public interface AccountHashtagUnit {

  AccountHashtagId create(AccountHashtag entity);

  void deleteAll(Account entity);

}
