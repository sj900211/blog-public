package run.freshr.domain.auth.unit.redis;

import org.apache.http.HttpException;
import run.freshr.common.extensions.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.auth.redis.ChatAccessRedis;

public interface ChatAccessRedisUnit extends UnitDocumentDefaultExtension<ChatAccessRedis, String> {

  Boolean existsByUsername(String username);

  ChatAccessRedis getByUsername(String username);

  void deleteByUsername(String username);

  ChatAccessRedis getManager() throws HttpException;

}
