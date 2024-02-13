package run.freshr.common.kafka;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Kafka 데이터 모델
 *
 * @author 류성재
 * @apiNote kafka 이벤트 전역에서 사용할 데이터 모델
 * @since 2023. 6. 22. 오전 9:51:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaData implements Serializable {

  /**
   * 이벤트
   *
   * @apiNote 이벤트를 정의<br>
   *          같은 토픽에서 이벤트로 구분하여 처리
   * @since 2023. 6. 22. 오전 9:51:01
   */
  private KafkaEvent event;
  /**
   * 데이터
   *
   * @apiNote 각 로직에서 필요한 정보
   * @since 2023. 6. 22. 오전 9:51:01
   */
  private Object data;

}
