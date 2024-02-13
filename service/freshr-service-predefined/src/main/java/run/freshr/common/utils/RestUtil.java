package run.freshr.common.utils;

import run.freshr.common.configurations.CustomConfig;
import run.freshr.common.data.ExceptionsData;
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
 *          {@link RestUtilAuthAware} 를 상속 받아 사용
 * @since 2023. 6. 16. 오전 9:55:58
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RestUtil extends RestUtilAuthAware {

  @Autowired
  public RestUtil(Environment environment, CustomConfig customConfig,
      ExceptionsData exceptionsData, AccountAuthUnit accountAuthUnit) {
    super(environment, customConfig, exceptionsData, accountAuthUnit);
  }

}
