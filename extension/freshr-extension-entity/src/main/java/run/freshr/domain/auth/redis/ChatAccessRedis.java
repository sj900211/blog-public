package run.freshr.domain.auth.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Slf4j
@RedisHash("MEM_CHAT_ACCESS")
@Getter
@Comment("Rocket Chat 토큰 관리")
public class ChatAccessRedis {

  @Id
  @Comment("일련 번호")
  private final String id;

  @Comment("토큰")
  private final String token;

  @Comment("아이디 - 이메일")
  private final String username;

  @Builder
  public ChatAccessRedis(String id, String token, String username) {
    log.info("ChatAccessRedis.Constructor");

    this.id = id;
    this.token = token;
    this.username = username;
  }

}
