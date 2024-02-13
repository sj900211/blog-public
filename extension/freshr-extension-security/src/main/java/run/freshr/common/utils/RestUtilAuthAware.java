package run.freshr.common.utils;

import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;
import static run.freshr.domain.auth.enumerations.Role.ROLE_MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.ROLE_MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.ROLE_USER;

import run.freshr.common.configurations.CustomConfigAware;
import run.freshr.common.data.ExceptionsData;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.auth.enumerations.Role;
import run.freshr.domain.auth.unit.jpa.AccountAuthUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 자주 사용하는 공통 기능을 정의
 *
 * @author 류성재
 * @apiNote 자주 사용하는 공통 기능을 정의<br>
 *          {@link RestUtilAware} 를 상속 받아 계정 관련 기능 추가
 * @since 2023. 6. 16. 오전 9:55:58
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestUtilAuthAware extends RestUtilAware {

  private static AccountAuthUnit accountAuthUnit;

  @Autowired
  public RestUtilAuthAware(Environment environment, CustomConfigAware customConfigAware,
      ExceptionsData exceptionsData, AccountAuthUnit accountAuthUnit) {
    super(environment, customConfigAware, exceptionsData);

    RestUtilAuthAware.accountAuthUnit = accountAuthUnit;
  }

  /**
   * 요청한 계정 일련 번호 조회
   *
   * @return signed id
   * @apiNote 요청한 계정 일련 번호 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:55:58
   */
  public static Long getSignedId() {
    log.info("RestUtil.getSignedId");

    return signedId.get();
  }

  /**
   * 요청한 계정 권한 조회
   *
   * @return signed role
   * @apiNote 요청한 계정 권한 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:55:58
   */
  public static Role getSignedRole() {
    log.info("RestUtil.getSignedRole");

    return signedRole.get();
  }

  /**
   * 요청한 계정 권한 체크
   *
   * @param role 권한
   * @return boolean
   * @apiNote 요청한 계정 권한 체크
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:55:58
   */
  public static boolean checkRole(Role role) {
    log.info("RestUtil.checkRole");

    return getSignedRole().check(role);
  }

  /**
   * 요청한 계정 권한이 시스템 관리자 권한인지 체크
   *
   * @return boolean
   * @apiNote 요청한 계정 권한이 시스템 관리자 권한인지 체크
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:55:58
   */
  public static boolean checkManager() {
    log.info("RestUtil.checkManager");

    return getSignedRole().check(ROLE_MANAGER_MAJOR, ROLE_MANAGER_MINOR);
  }

  /**
   * 요청한 계정 권한이 사용자 권한인지 체크
   *
   * @return boolean
   * @apiNote 요청한 계정 권한이 사용자 권한인지 체크
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:55:58
   */
  public static boolean checkUser() {
    log.info("RestUtil.checkUser");

    return getSignedRole().check(ROLE_USER);
  }

  /**
   * 요청한 계정 정보를 조회
   *
   * @return signed
   * @apiNote 요청한 계정 정보를 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:55:58
   */
  public static Account getSigned() {
    log.info("RestUtil.getSigned");

    return accountAuthUnit.get(getSignedId());
  }

}
