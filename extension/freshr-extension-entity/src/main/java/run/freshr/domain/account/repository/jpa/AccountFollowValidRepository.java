package run.freshr.domain.account.repository.jpa;

import run.freshr.domain.account.entity.embedded.AccountFollowId;
import run.freshr.domain.account.entity.mapping.AccountFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountFollowValidRepository extends
    JpaRepository<AccountFollow, AccountFollowId> {

}
