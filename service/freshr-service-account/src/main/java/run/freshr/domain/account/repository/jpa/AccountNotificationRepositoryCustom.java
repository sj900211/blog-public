package run.freshr.domain.account.repository.jpa;

import run.freshr.common.data.CursorData;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.AccountNotification;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.vo.AccountSearch;

public interface AccountNotificationRepositoryCustom {

  CursorData<AccountNotification> getPage(AccountSearch search, Account entity,
      AccountNotificationInheritanceType division);

}
