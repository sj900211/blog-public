package run.freshr.domain.account.unit.jpa;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountFollowId;
import run.freshr.domain.account.entity.mapping.AccountFollow;
import run.freshr.domain.account.repository.jpa.AccountFollowRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountFollowUnitImpl implements AccountFollowUnit {

  private final AccountFollowRepository repository;

  @Override
  @Transactional
  public AccountFollowId create(AccountFollow entity) {
    log.info("AccountFollowUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(AccountFollowId id) {
    log.info("AccountFollowUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public AccountFollow get(AccountFollowId id) {
    log.info("AccountFollowUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(AccountFollowId id) {
    log.info("AccountFollowUnit.delete");

    repository.deleteById(id);
  }

  @Override
  public List<AccountFollow> getAccountFollowerList(Account following) {
    log.info("AccountFollowUnit.getAccountFollowerList");

    return repository.getFollowerList(following);
  }

  @Override
  public List<AccountFollow> getAccountFollowingList(Account follower) {
    log.info("AccountFollowUnit.getAccountFollowingList");

    return repository.getFollowingList(follower);
  }

}
