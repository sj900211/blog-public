package run.freshr.domain.account.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountHashtagId;
import run.freshr.domain.account.entity.mapping.AccountHashtag;

public interface AccountHashtagRepository extends JpaRepository<AccountHashtag, AccountHashtagId> {

  void deleteAllByAccount(Account entity);

}
