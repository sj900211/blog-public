package run.freshr.domain.account.validator;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.utils.RestUtilAware.rejectRequired;
import static run.freshr.common.utils.RestUtilAware.rejectWrong;

import java.time.LocalDate;
import java.time.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import run.freshr.common.utils.RestUtil;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.vo.AccountSearch;

@Component
@RequiredArgsConstructor
public class AccountValidator {

  public void existsAccountByUsername(AccountSearch search, Errors errors) {
    String word = search.getWord();

    if (!hasLength(word)) {
      rejectRequired("word", errors);
    }
  }

  public void existsAccountByNickname(AccountSearch search, Errors errors) {
    String word = search.getWord();

    if (!hasLength(word)) {
      rejectRequired("word", errors);
    }
  }

  public void join(AccountJoinRequest dto, BindingResult bindingResult) {
    LocalDate birth = dto.getBirth();
    Period between = Period.between(birth, LocalDate.now());
    int age = between.getYears();

    if (age < 14) {
      RestUtil.rejectWrong("birth",
          "No one under the age of 14 can sign up.", bindingResult);
    }
  }

  public void getAccountPage(AccountSearch search, Errors errors) {
    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }
  }

  public void getNotificationByTypePage(AccountSearch search, String type, Errors errors) {
    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired("size", errors);
    }

    AccountNotificationInheritanceType division = AccountNotificationInheritanceType.find(type);

    if (isNull(division)) {
      rejectWrong("type", "An invalid value was entered for type.", errors);
    }
  }

}
