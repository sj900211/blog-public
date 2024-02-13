package run.freshr.domain.auth.unit.jpa;

import run.freshr.common.annotations.Unit;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.auth.repository.jpa.AccountAuthRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 계정 정보 관리 unit 구현 class
 *
 * @author 류성재
 * @apiNote 사용자 계정 정보 관리 unit 구현 class
 * @since 2023. 6. 16. 오전 11:08:45
 */
@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountAuthUnitImpl implements AccountAuthUnit {

  private final AccountAuthRepository repository;

  /**
   * 데이터 등록
   *
   * @param entity 데이터
   * @return long
   * @apiNote 데이터 등록
   * @author 류성재
   * @since 2023. 6. 16. 오전 11:08:45
   */
  @Override
  @Transactional
  public Long create(Account entity) {
    log.info("AccountUnit.create");

    return repository.save(entity).getId();
  }

  /**
   * 데이터 존재 여부
   *
   * @param id 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 16. 오전 11:08:45
   */
  @Override
  public Boolean exists(Long id) {
    log.info("AccountUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Boolean exists(String username) {
    log.info("AccountUnit.exists");

    return repository.existsByUsername(username);
  }

  /**
   * 데이터 조회
   *
   * @param id 일련 번호
   * @return account
   * @apiNote 데이터 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 11:08:45
   */
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

}
