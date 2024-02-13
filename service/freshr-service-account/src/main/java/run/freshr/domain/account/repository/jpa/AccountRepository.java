package run.freshr.domain.account.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

  Boolean existsByUsername(String username);
  Boolean existsByNickname(String nickname);
  Account findByUsername(String username);

}
