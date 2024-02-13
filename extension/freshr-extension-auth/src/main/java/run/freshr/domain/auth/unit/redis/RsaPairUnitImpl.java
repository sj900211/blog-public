package run.freshr.domain.auth.unit.redis;

import run.freshr.common.annotations.Unit;
import run.freshr.domain.auth.redis.RsaPair;
import run.freshr.domain.auth.repository.redis.RsaPairRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * RSA 키 관리 unit 구현 class
 *
 * @author 류성재
 * @apiNote RSA 키 관리 unit 구현 class
 * @since 2023. 6. 15. 오후 5:55:43
 */
@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RsaPairUnitImpl implements RsaPairUnit {

  private final RsaPairRepository repository;

  /**
   * 저장
   *
   * @param redis Document
   * @apiNote 저장
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:55:43
   */
  @Override
  @Transactional
  public void save(RsaPair redis) {
    log.info("RsaPairUnit.save");

    repository.save(redis);
  }

  /**
   * 데이터 존재 여부
   *
   * @param id 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:55:43
   */
  @Override
  public Boolean exists(String id) {
    log.info("RsaPairUnit.exists");

    return repository.existsById(id);
  }

  /**
   * 데이터 조회
   *
   * @param id 일련 번호
   * @return rsa pair
   * @apiNote 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:55:43
   */
  @Override
  public RsaPair get(String id) {
    log.info("RsaPairUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  /**
   * 삭제
   *
   * @param id 일련 번호
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:55:43
   */
  @Override
  @Transactional
  public void delete(String id) {
    log.info("RsaPairUnit.delete");

    repository.deleteById(id);
  }

  /**
   * 삭제
   *
   * @param list 데이터 목록
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:55:43
   */
  @Override
  @Transactional
  public void delete(Iterable<RsaPair> list) {
    log.info("RsaPairUnit.delete");

    repository.deleteAll(list);
  }

  /**
   * 데이터 목록 조회
   *
   * @return list
   * @apiNote 데이터 목록 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:55:43
   */
  @Override
  public Iterable<RsaPair> getList() {
    log.info("RsaPairUnit.getList");

    return repository.findAll();
  }

  /**
   * 유효 기간 검증
   *
   * @param encodePublicKey BASE64 로 인코딩된 RSA 공개 키
   * @param limit           유효 기간
   * @return boolean
   * @apiNote 유효 기간 검증
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:55:43
   */
  @Override
  @Transactional
  public Boolean checkRsa(String encodePublicKey, Long limit) {
    log.info("RsaPairUnit.checkRsa");

    Optional<RsaPair> optional = repository.findById(encodePublicKey);

    if (optional.isEmpty()) {
      return false;
    }

    RsaPair redis = optional.get();

    if (redis.getCreateAt().plusSeconds(limit).isBefore(LocalDateTime.now())) {
      repository.deleteById(encodePublicKey);

      return false;
    }

    return true;
  }

}
