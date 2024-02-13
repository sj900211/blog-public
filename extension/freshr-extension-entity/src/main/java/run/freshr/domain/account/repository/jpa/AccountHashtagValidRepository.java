package run.freshr.domain.account.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.account.entity.embedded.AccountHashtagId;
import run.freshr.domain.account.entity.mapping.AccountHashtag;

public interface AccountHashtagValidRepository extends
    JpaRepository<AccountHashtag, AccountHashtagId> {

}
