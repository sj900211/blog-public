package run.freshr.domain.account.enumerations;

import static run.freshr.common.utils.QueryUtilAware.searchKeyword;
import static run.freshr.domain.account.entity.QAccount.account;
import static java.util.Arrays.stream;
import static java.util.List.of;

import lombok.Getter;
import run.freshr.common.extensions.enumerations.SearchEnumExtension;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 계정 검색 조건 데이터 관리
 *
 * @author 류성재
 * @apiNote 계정 검색 조건 데이터 관리
 * @since 2023. 6. 16. 오전 10:50:56
 */
@Slf4j
public enum AccountSearchKeys implements SearchEnumExtension {

  ALL("전체", of(account.username, account.nickname)),
  USERNAME("아이디", of(account.username)),
  NICKNAME("닉네임", of(account.nickname));

  private final String value;
  @Getter
  private final List<StringPath> paths;

  AccountSearchKeys(String value, List<StringPath> paths) {
    this.value = value;
    this.paths = paths;
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
   * 검색 Query 작성
   *
   * @param word 검색어
   * @return boolean builder
   * @apiNote 검색어로 조회 조건 Query 를 작성
   * @author 류성재
   * @since 2023. 6. 16. 오전 10:52:39
   */
  @Override
  public BooleanBuilder search(String word) {
    return searchKeyword(word, paths);
  }

  /**
   * 열거형 데이터 조회
   *
   * @param key 열거형 데이터 name
   * @return role
   * @apiNote 열거형 데이터 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 10:52:14
   */
  public static AccountSearchKeys find(String key) {
    log.info("AccountSearchKeys.find");

    return stream(AccountSearchKeys.values())
        .filter(item -> item.getKey().equalsIgnoreCase(key))
        .findAny()
        .orElse(ALL);
  }

}
