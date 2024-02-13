package run.freshr.domain.auth.repository.jpa;

import run.freshr.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사용자 계정 정보 관리 repository
 *
 * @author 류성재
 * @apiNote 사용자 계정 정보 관리 repository
 * @since 2023. 6. 16. 오전 10:59:20
 */
public interface AccountAuthRepository extends JpaRepository<Account, Long> {

  Boolean existsByUsername(String username);

  Account findByUsername(String username);

}
