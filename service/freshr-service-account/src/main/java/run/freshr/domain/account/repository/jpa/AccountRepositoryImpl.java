package run.freshr.domain.account.repository.jpa;

import static run.freshr.domain.account.entity.QAccount.account;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasLength;

import run.freshr.common.data.CursorData;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.utils.QueryUtilAware;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.enumerations.AccountSearchKeys;
import run.freshr.domain.account.enumerations.AccountStatus;
import run.freshr.domain.account.vo.AccountSearch;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public CursorData<Account> getPage(AccountSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Account> query = queryFactory.selectFrom(account)
        .where(account.deleteFlag.isFalse(),
            account.useFlag.isTrue(),
            account.createAt.before(cursorAt));

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(QueryUtilAware.searchEnum(word, AccountSearchKeys.find(search.getKey())));
    }

    AccountStatus status = search.getStatus();
    Gender gender = search.getGender();
    LocalDate birthStartDate = search.getBirthStartDate();
    LocalDate birthEndDate = search.getBirthEndDate();

    if (!isNull(status)) {
      query.where(account.status.eq(status));
    }

    if (!isNull(gender)) {
      query.where(account.gender.eq(gender));
    }

    if (!isNull(birthStartDate)) {
      query.where(account.birth.goe(birthStartDate));
    }

    if (!isNull(birthEndDate)) {
      query.where(account.birth.loe(birthEndDate));
    }

    return QueryUtilAware.paging(query, account, search);
  }

}
