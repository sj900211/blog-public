package run.freshr.domain.auth.unit.jpa;

import run.freshr.common.extensions.unit.UnitDefaultExtension;
import run.freshr.domain.account.entity.Account;

/**
 * 사용자 계정 정보 관리 unit
 *
 * @author 류성재
 * @apiNote 사용자 계정 정보 관리 unit
 * @since 2023. 6. 16. 오전 11:07:02
 */
public interface AccountAuthUnit extends UnitDefaultExtension<Account, Long> {

  Boolean exists(String username);

  Account get(String username);

}
