package run.freshr.common.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum Visibility implements EnumModel {

  PUBLIC("공개"),
  PRIVATE("비공개");

  private final String value;

  Visibility(String value) {
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

  public static Visibility find(String key) {
    return Arrays.stream(Visibility.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
