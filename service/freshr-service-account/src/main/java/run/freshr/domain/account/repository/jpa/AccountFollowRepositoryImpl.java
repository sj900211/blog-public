package run.freshr.domain.account.repository.jpa;

import static run.freshr.domain.account.entity.mapping.QAccountFollow.accountFollow;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.mapping.AccountFollow;

@RequiredArgsConstructor
public class AccountFollowRepositoryImpl implements AccountFollowRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<AccountFollow> getFollowerList(Account following) {
    return queryFactory.selectFrom(accountFollow)
        .where(accountFollow.following.eq(following),
            accountFollow.follower.deleteFlag.isFalse(),
            accountFollow.follower.useFlag.isTrue())
        .fetch();
  }

  @Override
  public List<AccountFollow> getFollowingList(Account follower) {
    return queryFactory.selectFrom(accountFollow)
        .where(accountFollow.follower.eq(follower),
            accountFollow.following.deleteFlag.isFalse(),
            accountFollow.following.useFlag.isTrue())
        .fetch();
  }

}
