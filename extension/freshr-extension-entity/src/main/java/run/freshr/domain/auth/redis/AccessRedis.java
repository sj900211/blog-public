package run.freshr.domain.auth.redis;

import lombok.Builder;
import org.hibernate.annotations.Comment;
import run.freshr.domain.auth.enumerations.Role;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Access 토큰 관리
 *
 * @author 류성재
 * @apiNote Access 토큰 관리 Document
 * @since 2023. 6. 15. 오후 5:03:09
 */
@Slf4j
@RedisHash("MEM_ACCESS_TOKEN")
@Getter
@Comment("접근 토큰 관리")
public class AccessRedis {

  /**
   * 일련 번호
   *
   * @apiNote 일련 번호
   * @since 2023. 6. 15. 오후 5:03:09
   */
  @Id
  @Comment("일련 번호 - Access 토큰")
  private final String id;

  /**
   * 계정 일련 번호
   *
   * @apiNote 계정 일련 번호
   * @since 2023. 6. 15. 오후 5:03:09
   */
  @Comment("계정 일련 번호")
  private final Long signId;

  /**
   * 권한
   *
   * @apiNote 권한
   * @since 2023. 6. 15. 오후 5:03:09
   */
  @Comment("권한")
  private final Role role;

  /**
   * 생성자
   *
   * @param id     일련 번호
   * @param signId 계정 일련 번호
   * @param role   권한
   * @apiNote 생성자
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:03:09
   */
  @Builder
  public AccessRedis(String id, Long signId, Role role) {
    log.info("AccessRedis.Constructor");

    this.id = id;
    this.signId = signId;
    this.role = role;
  }

}
