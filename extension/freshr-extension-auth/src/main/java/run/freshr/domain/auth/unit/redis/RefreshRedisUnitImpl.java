package run.freshr.domain.auth.unit.redis;

import static java.util.Objects.isNull;

import run.freshr.common.annotations.Unit;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.auth.repository.redis.RefreshRedisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * Refresh 토큰 관리 unit 구현 class
 *
 * @author 류성재
 * @apiNote Refresh 토큰 관리 unit 구현 class
 * @since 2023. 6. 15. 오후 5:52:14
 */
@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshRedisUnitImpl implements RefreshRedisUnit {

  private final RefreshRedisRepository repository;

  /**
   * 저장
   *
   * @param redis Document
   * @apiNote 저장
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:52:14
   */
  @Override
  @Transactional
  public void save(RefreshRedis redis) {
    log.info("RefreshRedisUnit.save");

    repository.save(redis);
  }

  /**
   * 데이터 존재 여부
   *
   * @param id 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:52:14
   */
  @Override
  public Boolean exists(String id) {
    log.info("RefreshRedisUnit.exists");

    return repository.existsById(id);
  }

  /**
   * 데이터 조회
   *
   * @param id 일련 번호
   * @return refresh redis
   * @apiNote 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:52:14
   */
  @Override
  public RefreshRedis get(String id) {
    log.info("RefreshRedisUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * 삭제
   *
   * @param id 일련 번호
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:52:14
   */
  @Override
  @Transactional
  public void delete(String id) {
    log.info("RefreshRedisUnit.delete");

    repository.deleteById(id);
  }

  /**
   * 삭제
   *
   * @param access Access 토큰 정보
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:52:15
   */
  @Override
  @Transactional
  public void delete(AccessRedis access) {
    log.info("RefreshRedisUnit.delete");

    Iterable<RefreshRedis> refreshIterable = repository.findAll();

    refreshIterable.forEach(refresh -> {
      if (!isNull(refresh) && !isNull(refresh.getAccess())
          && refresh.getAccess().getId().equals(access.getId())) {
        repository.delete(refresh);
      }
    });
  }

}
