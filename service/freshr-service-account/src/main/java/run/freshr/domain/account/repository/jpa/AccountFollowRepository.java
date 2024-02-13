package run.freshr.domain.account.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.account.entity.embedded.AccountFollowId;
import run.freshr.domain.account.entity.mapping.AccountFollow;

public interface AccountFollowRepository extends JpaRepository<AccountFollow, AccountFollowId>,
    AccountFollowRepositoryCustom {

}
