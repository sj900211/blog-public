package run.freshr.domain.account.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum AccountNotificationInheritanceType implements EnumModel {

  ACCOUNT("사용자"),
  BLOG("블로그"),
  POST("포스팅"),
  POST_COMMENT("포스팅 댓글");

  private final String value;

  AccountNotificationInheritanceType(String value) {
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

  public static AccountNotificationInheritanceType find(String key) {
    return Arrays.stream(AccountNotificationInheritanceType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

  public static class Constant {
    public static final String ACCOUNT = "ACCOUNT";
    public static final String BLOG = "BLOG";
    public static final String POST = "POST";
    public static final String POST_COMMENT = "POST_COMMENT";
  }

}
