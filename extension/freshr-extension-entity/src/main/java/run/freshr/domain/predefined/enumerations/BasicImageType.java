package run.freshr.domain.predefined.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum BasicImageType implements EnumModel {

  PROFILE("사용자 프로필 이미지"),
  BLOG("블로그 커버 이미지");

  private final String value;

  BasicImageType(String value) {
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

  public static BasicImageType find(String key) {
    return Arrays.stream(BasicImageType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
