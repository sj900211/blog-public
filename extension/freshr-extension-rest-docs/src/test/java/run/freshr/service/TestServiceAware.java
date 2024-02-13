package run.freshr.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.boot.ApplicationRunner;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.mappers.EnumGetter;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.auth.enumerations.Privilege;
import run.freshr.domain.auth.enumerations.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.redis.RefreshRedis;
import run.freshr.domain.predefined.entity.Attach;

/**
 * 테스트 데이터 관리 service 공통 기능
 *
 * @author 류성재
 * @apiNote {@link ApplicationRunner} 를 상속받은 test runner 에서<br>
 *          데이터 설정을 쉽게하기 위해서 공통 데이터 생성 기능을 재정의<br>
 *          필수 작성은 아니며, 테스트 코드에서 데이터 생성 기능을 조금이라도 더 편하게 사용하고자 만든 Service<br>
 *          권한과 같은 특수한 경우를 제외한 대부분은 데이터에 대한 Create, Get 정도만 작성해서 사용을 한다.
 * @since 2023. 1. 13. 오전 11:35:07
 */
public interface TestServiceAware {

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  Map<String, List<EnumGetter>> getEnumAll();

  //      ___      __    __  .___________. __    __
  //     /   \    |  |  |  | |           ||  |  |  |
  //    /  ^  \   |  |  |  | `---|  |----`|  |__|  |
  //   /  /_\  \  |  |  |  |     |  |     |   __   |
  //  /  _____  \ |  `--'  |     |  |     |  |  |  |
  // /__/     \__\ \______/      |__|     |__|  |__|

  void createRsa();

  void createAuth(Long id, Role role);

  void createAccess(String accessToken, Long id, Role role);

  AccessRedis getAccess(String id);

  void createRefresh(String refreshToken, String access);

  RefreshRedis getRefresh(String refreshToken);

  long createAccount(String prefix, String suffix, Privilege privilege, Gender gender,
      Attach profile);

  Account getAccount(long id);

}
