package run.freshr.domain.account.unit.jpa;

import static run.freshr.common.utils.EntityValidateUtil.validateLogical;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.common.data.CursorData;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.repository.jpa.AccountRepository;
import run.freshr.domain.account.vo.AccountSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountUnitImpl implements AccountUnit {

  private final AccountRepository repository;

  @Override
  @Transactional
  public Long create(Account entity) {
    log.info("AccountUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(Long id) {
    log.info("AccountUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Boolean existsByUsername(String username) {
    log.info("AccountUnit.existsByUsername");

    return repository.existsByUsername(username);
  }

  @Override
  public Boolean existsByNickname(String nickname) {
    log.info("AccountUnit.existsByNickname");

    return repository.existsByNickname(nickname);
  }

  @Override
  public Account get(Long id) {
    log.info("AccountUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Account get(String username) {
    log.info("AccountUnit.get");

    return repository.findByUsername(username);
  }

  @Override
  public CursorData<Account> getPage(AccountSearch search) {
    log.info("AccountUnit.getPage");

    return repository.getPage(search);
  }

  @Override
  public boolean validateAccount(Long id) {
    return validateLogical(repository.findById(id));
  }

}
