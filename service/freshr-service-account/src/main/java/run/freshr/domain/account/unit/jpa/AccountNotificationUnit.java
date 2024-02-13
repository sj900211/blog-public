package run.freshr.domain.account.unit.jpa;

import run.freshr.common.data.CursorData;
import run.freshr.common.extensions.unit.UnitDefaultExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.AccountNotification;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.vo.AccountSearch;

public interface AccountNotificationUnit extends
    UnitDefaultExtension<AccountNotification, String> {

  CursorData<AccountNotification> getPage(AccountSearch search, Account account);

  CursorData<AccountNotification> getPage(AccountSearch search, Account account,
      AccountNotificationInheritanceType division);

  Long countUnread(Account account);

}
