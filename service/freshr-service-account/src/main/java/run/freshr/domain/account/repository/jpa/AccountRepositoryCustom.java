package run.freshr.domain.account.repository.jpa;

import run.freshr.common.data.CursorData;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.vo.AccountSearch;

public interface AccountRepositoryCustom {

  CursorData<Account> getPage(AccountSearch search);

}
