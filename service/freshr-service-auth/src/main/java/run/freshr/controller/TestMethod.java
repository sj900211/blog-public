package run.freshr.controller;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public enum TestMethod {

  AUTH, TOGGLE;

  @Nullable
  public static TestMethod resolve(String method) {
    Assert.notNull(method, "Method must not be null");
    return switch (method) {
      case "AUTH" -> AUTH;
      case "TOGGLE" -> TOGGLE;
      default -> null;
    };
  }

  @Nullable
  public static TestMethod resolve(HttpMethod httpMethod) {
    Assert.notNull(httpMethod, "HttpMethod must not be null");
    return resolve(httpMethod.name());
  }

  public TestMethod asHttpMethod() {
    return switch (this) {
      case AUTH -> TestMethod.AUTH;
      case TOGGLE -> TestMethod.TOGGLE;
    };
  }

}
