package run.freshr.common.kafka;

/**
 * Kafka 토픽 관리
 *
 * @author 류성재
 * @apiNote enum 으로 관리하지 않는 이유는 annotation 에서 사용하기 위해 따로 관리.<br>
 *          중앙 관리가 좋을지 아직 판단이 되지 않아 사용해보면서 판단 필요
 * @since 2023. 6. 22. 오전 9:55:43
 */
public class KafkaTopic {

  public static final String LOGGING_CREATE = "logging.create";
  public static final String STATISTIC_UPSERT = "statistic.upsert";

}
