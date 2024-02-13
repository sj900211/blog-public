package run.freshr.domain.account.unit.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountHashtagId;
import run.freshr.domain.account.entity.mapping.AccountHashtag;
import run.freshr.domain.account.repository.jpa.AccountHashtagRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountHashtagUnitImpl implements AccountHashtagUnit {

  private final AccountHashtagRepository repository;

  @Override
  @Transactional
  public AccountHashtagId create(AccountHashtag entity) {
    log.info("AccountHashtagUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  @Transactional
  public void deleteAll(Account entity) {
    log.info("AccountHashtagUnit.deleteAll");

    repository.deleteAllByAccount(entity);
  }

}
