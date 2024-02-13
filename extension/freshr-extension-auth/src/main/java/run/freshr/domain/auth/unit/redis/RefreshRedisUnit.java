package run.freshr.domain.auth.unit.redis;

import run.freshr.common.extensions.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;

/**
 * Refresh 토큰 관리 unit
 *
 * @author 류성재
 * @apiNote Refresh 토큰 관리 unit
 * @since 2023. 6. 15. 오후 5:13:53
 */
public interface RefreshRedisUnit extends UnitDocumentDefaultExtension<RefreshRedis, String> {

  /**
   * 삭제
   *
   * @param access Access 토큰 정보
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:13:53
   */
  void delete(AccessRedis access);

}
