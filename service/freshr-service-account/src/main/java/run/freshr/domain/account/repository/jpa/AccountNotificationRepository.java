package run.freshr.domain.account.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.AccountNotification;

public interface AccountNotificationRepository extends JpaRepository<AccountNotification, String>,
    AccountNotificationRepositoryCustom {

  Long countByAccountAndReadIsFalse(Account account);

}
