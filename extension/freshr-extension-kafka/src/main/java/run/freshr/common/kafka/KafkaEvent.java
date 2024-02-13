package run.freshr.common.kafka;

import static java.util.Arrays.stream;

import run.freshr.common.mappers.EnumModel;

/**
 * Kafka 이벤트 관리
 *
 * @author 류성재
 * @apiNote 이벤트를 한 곳에서 관리하기 위해 만든 클래스<br>
 *          중앙 관리가 좋을지 아직 판단이 되지 않아 사용해보면서 판단 필요
 * @since 2023. 6. 22. 오전 9:53:43
 */
public enum KafkaEvent implements EnumModel {

  LOGGING_PREDEFINED_BASIC_IMAGE("사전 정의 관리 > 기본 이미지 관리 로그"),
  LOGGING_ACCOUNT_INFO("사용자 관리 > 계정 관리 로그"),
  LOGGING_BLOG_INFO("블로그 관리 > 블로그 정보 관리 로그"),
  LOGGING_BLOG_POST("블로그 관리 > 포스팅 관리 로그"),
  LOGGING_BLOG_POST_COMMENT("블로그 관리 > 포스팅 댓글 관리 로그"),
  LOGGING_CONVERSATION_CHANNEL("대화 관리 > 채널 관리 로그"),

  STATISTIC_HASHTAG("통계 관리 > 해시태그 통계");

  private final String value;

  KafkaEvent(String value) {
    this.value = value;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return this.value;
  }

  public static KafkaEvent find(String key) {
    return stream(KafkaEvent.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

}
