package run.freshr.domain.auth.repository.redis;

import run.freshr.domain.auth.redis.RefreshRedis;
import org.springframework.data.repository.CrudRepository;

/**
 * Refresh 토큰 관리 repository
 *
 * @author 류성재
 * @apiNote Refresh 토큰 관리 repository
 * @since 2023. 6. 15. 오후 5:09:54
 */
public interface RefreshRedisRepository extends CrudRepository<RefreshRedis, String> {

}
