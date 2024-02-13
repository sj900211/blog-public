package run.freshr.common.configurations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

/**
 * 필수 추가 설정 항목 설정
 *
 * @author 류성재
 * @apiNote 필수 추가 설정 항목 설정
 * @since 2023. 6. 16. 오후 1:15:39
 */
@Data
@Configuration
@EnableConfigurationProperties
public abstract class CustomConfigAware {

  /**
   * Health Check 화면 경로
   *
   * @apiNote Health Check 화면 경로<br>
   *          화면은 필요가 없음<br>
   *          류성재 개인의 욕심
   * @since 2023. 6. 16. 오후 1:15:39
   */
  @Value("classpath:static/heartbeat/index.htm")
  private Resource heartbeat;

  /**
   * RSA 공개키 유지 기간
   *
   * @apiNote RSA 공개키 유지 기간<br>
   *          millisecond 단위
   * @since 2023. 6. 16. 오후 1:15:39
   */
  @Value("${freshr.rsa.limit}")
  private Integer rsaLimit;

  /**
   * 인증인가 유지 기간
   *
   * @apiNote 인증인가 유지 기간<br>
   *          millisecond 단위<br>
   *          마지막 갱신 시점으로부터 유지 기간이 지난 후 갱신받으려 하면 로그아웃 처리
   * @since 2023. 6. 16. 오후 1:15:39
   */
  @Value("${freshr.auth.limit}")
  private Long authLimit;

}
