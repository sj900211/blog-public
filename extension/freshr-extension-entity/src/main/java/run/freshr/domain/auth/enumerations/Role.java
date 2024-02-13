package run.freshr.domain.auth.enumerations;

import static java.util.Arrays.stream;

import run.freshr.common.mappers.EnumModel;
import java.util.Arrays;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 권한 데이터 관리
 *
 * @author 류성재
 * @apiNote 권한 데이터 관리
 * @since 2023. 6. 15. 오후 4:58:26
 */
@Slf4j
public enum Role implements EnumModel {

  ROLE_MANAGER_MAJOR("시스템 관리자", Privilege.MANAGER_MAJOR),
  ROLE_MANAGER_MINOR("관리자", Privilege.MANAGER_MINOR),
  ROLE_USER("사용자", Privilege.USER),
  ROLE_ANONYMOUS("게스트", Privilege.ANONYMOUS);

  private final String value;
  @Getter
  private final Privilege privilege;

  Role(String value, Privilege privilege) {
    this.value = value;
    this.privilege = privilege;
  }

  /**
   * 열거형 데이터 조회
   *
   * @param key 열거형 데이터 name
   * @return role
   * @apiNote 열거형 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:58:46
   */
  public static Role find(String key) {
    log.info("Role.find");

    return stream(Role.values())
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
    return value;
  }

  /**
   * 권한 체크
   *
   * @param roles 권한 목록
   * @return boolean
   * @apiNote 권한 체크
   * @author 류성재
   * @since 2023. 6. 15. 오후 5:00:10
   */
  public boolean check(Role... roles) {
    return Arrays.asList(roles).contains(this);
  }

  /**
   * 권한 목록 정의
   *
   * @author 류성재
   * @apiNote Annotation 에서 사용하기 위해 권한 name 을 불변 속성으로 정의
   * @since 2023. 6. 15. 오후 5:00:41
   */
  public static class Secured {

    public static final String MANAGER_MAJOR = "ROLE_MANAGER_MAJOR";
    public static final String MANAGER_MINOR = "ROLE_MANAGER_MINOR";
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  }

}
