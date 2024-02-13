package run.freshr.domain.account.unit.jpa;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.common.data.CursorData;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.AccountNotification;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.repository.jpa.AccountNotificationRepository;
import run.freshr.domain.account.vo.AccountSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountNotificationUnitImpl implements AccountNotificationUnit {

  private final AccountNotificationRepository repository;

  @Override
  @Transactional
  public String create(AccountNotification entity) {
    log.info("AccountNotificationUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  public Boolean exists(String id) {
    log.info("AccountNotificationUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public AccountNotification get(String id) {
    log.info("AccountNotificationUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public CursorData<AccountNotification> getPage(AccountSearch search, Account account) {
    log.info("AccountNotificationUnit.getPage");

    return repository.getPage(search, account, null);
  }

  @Override
  public CursorData<AccountNotification> getPage(AccountSearch search, Account account,
      AccountNotificationInheritanceType division) {
    log.info("AccountNotificationUnit.getPage");

    return repository.getPage(search, account, division);
  }

  @Override
  public Long countUnread(Account account) {
    log.info("AccountNotificationUnit.countUnread");

    return repository.countByAccountAndReadIsFalse(account);
  }

}
