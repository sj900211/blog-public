package run.freshr.domain.account.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.account.entity.inherit.AccountNotificationAccount;

public interface AccountNotificationAccountValidRepository extends
    JpaRepository<AccountNotificationAccount, String> {

}
