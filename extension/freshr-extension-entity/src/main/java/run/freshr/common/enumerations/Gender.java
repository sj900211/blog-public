package run.freshr.common.enumerations;

import run.freshr.common.mappers.EnumModel;
import java.util.Arrays;

public enum Gender implements EnumModel {

  MALE("남성"),
  FEMALE("여성"),
  OTHERS("그 외");

  private final String value;

  Gender(String value) {
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

  public static Gender find(String key) {
    return Arrays.stream(Gender.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
