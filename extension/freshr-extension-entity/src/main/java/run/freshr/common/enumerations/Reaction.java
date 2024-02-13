package run.freshr.common.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum Reaction implements EnumModel {

  LIKE("좋아요"),
  HATE("싫어요");

  private final String value;

  Reaction(String value) {
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

  public static Reaction find(String key) {
    return Arrays.stream(Reaction.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
