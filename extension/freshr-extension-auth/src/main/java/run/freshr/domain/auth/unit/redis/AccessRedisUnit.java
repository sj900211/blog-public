package run.freshr.domain.auth.unit.redis;

import run.freshr.common.extensions.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.auth.redis.AccessRedis;

/**
 * Access 토큰 관리 unit
 *
 * @author 류성재
 * @apiNote Access 토큰 관리 unit
 * @since 2023. 6. 15. 오후 5:10:42
 */
public interface AccessRedisUnit extends UnitDocumentDefaultExtension<AccessRedis, String> {

  /**
   * 데이터 존재 여부
   *
   * @param signId 계정 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:10:42
   */
  Boolean exists(Long signId);

  /**
   * 데이터 조회
   *
   * @param signId 계정 일련 번호
   * @return access redis
   * @apiNote 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:10:42
   */
  AccessRedis get(Long signId);

  /**
   * 데이터 목록 조회
   *
   * @return list
   * @apiNote 데이터 목록 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:10:42
   */
  Iterable<AccessRedis> getList();

  /**
   * 삭제
   *
   * @param signId 계정 일련 번호
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:10:42
   */
  void delete(Long signId);

}
