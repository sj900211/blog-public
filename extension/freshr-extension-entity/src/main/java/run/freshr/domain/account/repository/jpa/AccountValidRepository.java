package run.freshr.domain.account.repository.jpa;

import run.freshr.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountValidRepository extends JpaRepository<Account, Long> {

}
