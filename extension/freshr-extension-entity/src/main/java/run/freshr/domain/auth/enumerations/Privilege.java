package run.freshr.domain.auth.enumerations;

import static java.util.Arrays.stream;

import run.freshr.common.mappers.EnumModel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 권한 데이터 관리
 *
 * @author 류성재
 * @apiNote 권한 데이터 관리
 * @since 2023. 6. 15. 오후 4:57:21
 */
@Slf4j
public enum Privilege implements EnumModel {

  MANAGER_MAJOR("시스템 관리자", Role.ROLE_MANAGER_MAJOR),
  MANAGER_MINOR("관리자", Role.ROLE_MANAGER_MINOR),
  USER("사용자", Role.ROLE_USER),
  ANONYMOUS("게스트", Role.ROLE_ANONYMOUS);

  private final String value;
  @Getter
  private final Role role;

  Privilege(String value, Role role) {
    this.value = value;
    this.role = role;
  }

  /**
   * 열거형 데이터 조회
   *
   * @param key 열거형 데이터 name
   * @return privilege
   * @apiNote 열거형 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:57:59
   */
  public static Privilege find(String key) {
    log.info("Privilege.find");

    return stream(Privilege.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(null);
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return this.value;
  }

}
