package run.freshr.common.configurations;

import static java.util.Objects.isNull;

import run.freshr.common.security.TokenProvider;
import run.freshr.common.utils.RestUtilAuthAware;
import run.freshr.domain.account.entity.Account;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * JPA Auditing 구현체
 *
 * @author 류성재
 * @apiNote JPA Auditing 구현체<br>
 *          데이터 변동이 발생했을 경우 주체가 되는 계정 정보를 조회하는 로직을 작성
 * @since 2023. 6. 16. 오전 9:18:21
 */
@Component
public class AuditorAwareImpl implements AuditorAware<Account> {

  /**
   * 요청한 계정 정보 조회
   *
   * @return current auditor
   * @apiNote 요청한 계정 정보 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:18:21
   */
  @NotNull
  @Override
  public Optional<Account> getCurrentAuditor() {
    Long signedId = TokenProvider.signedId.get();
    Account signed = null;

    if (!isNull(signedId) && signedId > 0) {
      signed = RestUtilAuthAware.getSigned();
    }

    return Optional.ofNullable(signed);
  }

}
