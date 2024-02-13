package run.freshr.domain.auth.repository.redis;

import run.freshr.domain.auth.redis.AccessRedis;
import org.springframework.data.repository.CrudRepository;

/**
 * Access 토큰 관리 repository
 *
 * @author 류성재
 * @apiNote Access 토큰 관리 repository
 * @since 2023. 6. 15. 오후 5:09:31
 */
public interface AccessRedisRepository extends CrudRepository<AccessRedis, String> {

}
