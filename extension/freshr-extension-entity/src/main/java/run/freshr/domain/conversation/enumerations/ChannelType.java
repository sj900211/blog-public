package run.freshr.domain.conversation.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum ChannelType implements EnumModel {

  DIRECT("1:1"), // 1:1 대화방
  GROUP("그룹"), // 비공개 대화방. 초대만 가능
  TEAM("팀"), // TODO: 사용 정책 없음. 정해야함
  CHANNEL("채널"); // 공개 대화방. 초대, 참가 가능

  private final String value;

  ChannelType(String value) {
    this.value = value;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

  public static ChannelType find(String key) {
    return Arrays.stream(ChannelType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
