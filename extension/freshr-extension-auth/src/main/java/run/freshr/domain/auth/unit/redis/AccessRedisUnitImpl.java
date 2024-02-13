package run.freshr.domain.auth.unit.redis;

import run.freshr.common.annotations.Unit;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.repository.redis.AccessRedisRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * Access 토큰 관리 unit 구현 class
 *
 * @author 류성재
 * @apiNote Access 토큰 관리 unit 구현 class
 * @since 2023. 6. 15. 오후 5:28:18
 */
@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessRedisUnitImpl implements AccessRedisUnit {

  private final AccessRedisRepository repository;

  /**
   * 저장
   *
   * @param redis Document
   * @apiNote 저장
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  @Transactional
  public void save(AccessRedis redis) {
    log.info("AccessRedisUnit.save");

    repository.save(redis);
  }

  /**
   * 데이터 존재 여부
   *
   * @param id 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  public Boolean exists(String id) {
    log.info("AccessRedisUnit.exists");

    return repository.existsById(id);
  }

  /**
   * 데이터 존재 여부
   *
   * @param signId 계정 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  public Boolean exists(Long signId) {
    log.info("AccessRedisUnit.exists");

    Iterable<AccessRedis> accesses = repository.findAll();
    Optional<AccessRedis> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    return authAccess.isPresent();
  }

  /**
   * 데이터 조회
   *
   * @param id 일련 번호
   * @return access redis
   * @apiNote 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  public AccessRedis get(String id) {
    log.info("AccessRedisUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * 데이터 조회
   *
   * @param signId 계정 일련 번호
   * @return access redis
   * @apiNote 데이터 조회<br>
   *          계정의 첫 번째 데이터를 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  public AccessRedis get(Long signId) {
    log.info("AccessRedisUnit.get");

    Iterable<AccessRedis> accesses = repository.findAll();
    Optional<AccessRedis> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    return authAccess.orElseThrow(EntityNotFoundException::new);
  }

  /**
   * 데이터 목록 조회
   *
   * @return list
   * @apiNote 데이터 목록 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  public Iterable<AccessRedis> getList() {
    log.info("AccessRedisUnit.getList");

    return repository.findAll();
  }

  /**
   * 삭제
   *
   * @param id 일련 번호
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  @Transactional
  public void delete(String id) {
    log.info("AccessRedisUnit.delete");

    repository.deleteById(id);
  }

  /**
   * 삭제
   *
   * @param signId 계정 일련 번호
   * @apiNote 삭제<br>
   *          계정 정보로 조회된 모든 데이터 삭제 처리
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:28:18
   */
  @Override
  @Transactional
  public void delete(Long signId) {
    log.info("AccessRedisUnit.delete");

    Iterable<AccessRedis> accesses = repository.findAll();
    Optional<AccessRedis> authAccess = StreamSupport.stream(accesses.spliterator(), false)
        .filter(access -> access.getSignId().equals(signId))
        .findFirst();

    authAccess.ifPresent(repository::delete);
  }

}
