package run.freshr.domain.logging.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum LogPostType implements EnumModel {

  CREATE("생성"),
  UPDATE("수정"),
  VIEW("조회"),
  DELETE("삭제");

  private final String value;

  LogPostType(String value) {
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

  public static LogPostType find(String key) {
    return Arrays.stream(LogPostType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
