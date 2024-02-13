package run.freshr.domain.logging.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum LogBlogType implements EnumModel {

  CREATE("생성"),
  UPDATE("수정"),
  PARTICIPATE("참여자 정보 변동"),
  DELETE("삭제");

  private final String value;

  LogBlogType(String value) {
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

  public static LogBlogType find(String key) {
    return Arrays.stream(LogBlogType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
