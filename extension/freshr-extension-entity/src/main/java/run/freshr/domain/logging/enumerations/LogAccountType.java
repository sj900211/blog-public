package run.freshr.domain.logging.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum LogAccountType implements EnumModel {

  JOIN("가입"),
  IN("로그인"),
  UPDATE("계정 정보 수정"),
  PASSWORD("비밀번호 변경"),
  PRIVILEGE("권한 변경"),
  STATUS("상태 변경"),
  DORMANT("휴면"),
  WITHDRAWAL("탈퇴");

  private final String value;

  LogAccountType(String value) {
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

  public static LogAccountType find(String key) {
    return Arrays.stream(LogAccountType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
