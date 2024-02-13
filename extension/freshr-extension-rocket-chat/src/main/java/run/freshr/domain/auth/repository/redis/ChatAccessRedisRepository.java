package run.freshr.domain.auth.repository.redis;

import org.springframework.data.repository.CrudRepository;
import run.freshr.domain.auth.redis.ChatAccessRedis;

public interface ChatAccessRedisRepository extends CrudRepository<ChatAccessRedis, String> {

}
