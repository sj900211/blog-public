package run.freshr.domain.account.enumerations;

import run.freshr.common.mappers.EnumModel;
import java.util.Arrays;

public enum AccountStatus implements EnumModel {

  INACTIVE("비활성"),
  ACTIVE("활성"),
  DORMANT("휴면"),
  WITHDRAWAL("탈퇴");

  private final String value;

  AccountStatus(String value) {
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

  public static AccountStatus find(String key) {
    return Arrays.stream(AccountStatus.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
