package run.freshr.domain.account.enumerations;

import java.util.Arrays;
import run.freshr.common.mappers.EnumModel;

public enum AccountNotificationType implements EnumModel {

  ACCOUNT_FOLLOW("팔로우"),
  BLOG_SUBSCRIBE("블로그 구독"),
  POST_NEW("포스팅 등록"),
  POST_REACTION("포스팅 반응"),
  POST_COMMENT_NEW("포스팅 댓글 등록"),
  POST_COMMENT_REACTION("포스팅 댓글 반응");

  private final String value;

  AccountNotificationType(String value) {
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

  public static AccountNotificationType find(String key) {
    return Arrays.stream(AccountNotificationType.values())
        .filter(item -> item.getKey().equals(key.toUpperCase()))
        .findAny()
        .orElse(null);
  }

}
