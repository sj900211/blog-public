package run.freshr.domain.auth.redis;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

/**
 * Refresh 토큰 관리
 *
 * @author 류성재
 * @apiNote Refresh 토큰 관리 Document
 * @since 2023. 6. 15. 오후 5:05:16
 */
@Slf4j
@RedisHash("MEM_REFRESH_TOKEN")
@Getter
@Comment("갱신 토큰 관리")
public class RefreshRedis {

  /**
   * 일련 번호
   *
   * @apiNote 일련 번호
   * @since 2023. 6. 15. 오후 5:05:16
   */
  @Id
  @Comment("일련 번호 - Refresh 토큰")
  private final String id;

  /**
   * Access 토큰 정보
   *
   * @apiNote Access 토큰 정보
   * @since 2023. 6. 15. 오후 5:05:16
   */
  @Reference
  @Comment("Access 토큰")
  private AccessRedis access;

  /**
   * 마지막 수정 날짜 시간
   *
   * @apiNote 마지막 수정 날짜 시간
   * @since 2023. 6. 15. 오후 5:05:16
   */
  @Comment("마지막 갱신 날짜 시간")
  private LocalDateTime updateAt;

  /**
   * 생성자
   *
   * @param id     일련 번호
   * @param access Access 토큰 정보
   * @apiNote 생성자
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:05:16
   */
  @Builder
  public RefreshRedis(String id, AccessRedis access) {
    log.info("RefreshRedis.Constructor");

    this.id = id;
    this.access = access;
    this.updateAt = LocalDateTime.now();
  }

  /**
   * Access 토큰 정보 업데이트
   *
   * @param access Access 토큰 정보
   * @apiNote Access 토큰 정보 업데이트
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:05:16
   */
  public void updateRedis(AccessRedis access) {
    log.info("RefreshRedis.updateRedis");

    this.access = access;
    this.updateAt = LocalDateTime.now();
  }

}
