package run.freshr.common.kafka;

import run.freshr.common.kafka.KafkaData.KafkaDataBuilder;
import java.util.Arrays;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka 메시지 발송 기능 정의
 *
 * @author 류성재
 * @apiNote Kafka 메시지 발송 기능 정의
 * @since 2023. 6. 22. 오전 9:57:11
 */
@Slf4j
@Service
@AllArgsConstructor
public class KafkaProducer {

  private final Environment environment;

  /**
   * 발송 구조 정의
   *
   * @apiNote 발송 구조 정의
   * @since 2023. 6. 22. 오전 9:57:11
   */
  private final KafkaTemplate<String, KafkaData> template;

  /**
   * 발송
   *
   * @param topic topic 이름
   * @param event event 유형
   * @apiNote 발송
   * @author 류성재
   * @since 2023. 6. 22. 오전 9:57:11
   */
  public void publish(String topic, KafkaEvent event) {
    publish(topic, event, null);
  }

  /**
   * 발송
   *
   * @param topic topic 이름
   * @param event 이벤트 유형
   * @param data  발송 데이터
   * @apiNote 발송
   * @author 류성재
   * @since 2023. 6. 22. 오전 10:01:21
   */
  public void publish(String topic, KafkaEvent event, Object data) {
    KafkaDataBuilder builder = KafkaData.builder().event(event);

    if (!Objects.isNull(data)) {
      builder.data(data);
    }

    publish(topic, builder.build());
  }

  /**
   * 발송
   *
   * @param topic topic 이름
   * @param data  발송 데이터
   * @apiNote 발송
   * @author 류성재
   * @since 2023. 6. 22. 오전 9:57:11
   */
  public void publish(String topic, KafkaData data) {
    boolean except = Arrays.stream(environment.getActiveProfiles())
        .anyMatch(active -> active.equalsIgnoreCase("test"));


    if (!except) {
      template.send(topic, data);
    }
  }

}
