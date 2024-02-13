package run.freshr.domain.auth.redis;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * RSA 키 관리
 *
 * @author 류성재
 * @apiNote RSA 키 관리
 * @since 2023. 6. 15. 오후 5:07:39
 */
@Slf4j
@RedisHash("MEM_RSA_PAIR")
@Getter
@Comment("RSA 키 관리")
public class RsaPair {

  /**
   * 일련 번호
   *
   * @apiNote RSA 공개 키
   * @since 2023. 6. 15. 오후 5:07:39
   */
  @Id
  @Comment("일련 번호 - 공개 키")
  private final String publicKey;

  /**
   * RSA 비공개 키
   *
   * @apiNote RSA 비공개 키
   * @since 2023. 6. 15. 오후 5:07:39
   */
  @Comment("비공개 키")
  private final String privateKey;

  /**
   * 등록 날짜 시간
   *
   * @apiNote 등록 날짜 시간
   * @since 2023. 6. 15. 오후 5:07:39
   */
  @Comment("등록 날짜 시간")
  private final LocalDateTime createAt;

  /**
   * 생성자
   *
   * @param publicKey  RSA 공개 키
   * @param privateKey RSA 비공개 키
   * @param createAt   등록 날짜 시간
   * @apiNote 생성자
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:07:39
   */
  @Builder
  public RsaPair(String publicKey, String privateKey, LocalDateTime createAt) {
    log.info("RsaPair.Constructor");

    this.publicKey = publicKey;
    this.privateKey = privateKey;
    this.createAt = createAt;
  }

}
