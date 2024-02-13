package run.freshr.domain.logging.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum LogChannelType implements EnumModel {

  PARTICIPATE("참여자 정보 변동");

  private final String value;

  LogChannelType(String value) {
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

  public static LogChannelType find(String key) {
    return Arrays.stream(LogChannelType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
