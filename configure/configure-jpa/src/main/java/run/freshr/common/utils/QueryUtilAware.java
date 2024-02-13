package run.freshr.common.utils;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static org.springframework.data.domain.PageRequest.of;

import run.freshr.common.data.CursorData;
import run.freshr.common.extensions.enumerations.SearchEnumExtension;
import run.freshr.common.extensions.request.SearchExtension;
import run.freshr.common.functional.PagingFunctional;
import run.freshr.common.functional.SearchEnumFunctional;
import run.freshr.common.functional.SearchKeywordFunctional;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * QueryDsl 공통 기능
 *
 * @author 류성재
 * @apiNote QueryDsl 공통 기능 정의
 * @since 2023. 6. 15. 오후 4:13:13
 */
@Slf4j
@Component
public class QueryUtilAware {

  /**
   * 자연어 검색
   *
   * @param word  검색어
   * @param paths 검색 대상의 QueryDsl Path 목록
   * @return boolean builder
   * @apiNote 자연어 검색
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:13:13
   */
  public static BooleanBuilder searchKeyword(String word, List<StringPath> paths) {
    log.info("QueryUtilAware.searchKeyword");

    final SearchKeywordFunctional SEARCH_KEYWORD_FUNCTIONAL = (functionalWord, functionalPaths) -> {
      BooleanBuilder booleanBuilder = new BooleanBuilder();

      functionalPaths.forEach(path -> booleanBuilder.or(path.containsIgnoreCase(functionalWord)));

      return booleanBuilder;
    };

    return SEARCH_KEYWORD_FUNCTIONAL.search(word, paths);
  }

  /**
   * SearchEnumExtension 을 사용한 자연어 검색
   *
   * @param <E>         SearchEnumExtension 상속받은 검색 유형 Enum
   * @param word        검색어
   * @param enumeration SearchEnumExtension 상속받은 검색 유형 Enum
   * @return boolean builder
   * @apiNote SearchEnumExtension 을 사용한 자연어 검색
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:13:13
   */
  public static <E extends SearchEnumExtension> BooleanBuilder searchEnum
      (String word, E enumeration) {
    log.info("QueryUtilAware.searchEnum");

    final SearchEnumFunctional<E> SEARCH_ENUM_FUNCTIONAL =
        (functionalWord, functionalEnumeration) -> functionalEnumeration.search(functionalWord);

    return SEARCH_ENUM_FUNCTIONAL.search(word, enumeration);
  }

  /**
   * 페이징 처리
   *
   * @param <E>    Entity
   * @param <Q>    Entity 의 Path
   * @param <S>    SearchExtension 을 상속받은 Get Parameter VO
   * @param query  JPA Query
   * @param path   검색 대상의 QueryDsl Path
   * @param search SearchExtension 을 상속받은 Get Parameter VO
   * @return page
   * @apiNote 페이징 처리
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:13:13
   */
  public static <E, Q extends EntityPathBase<E>, S extends SearchExtension<?>> CursorData<E> paging
  (JPAQuery<E> query, Q path, S search) {
    log.info("QueryUtilAware.paging");

    return paging(query, path, search, null);
  }

  /**
   * Paging page.
   *
   * @param <E>       Entity
   * @param <Q>       Entity 의 Path
   * @param <S>       SearchExtension 을 상속받은 Get Parameter VO
   * @param query     JPA Query
   * @param path      검색 대상의 QueryDsl Path
   * @param search    SearchExtension 을 상속받은 Get Parameter VO
   * @param orderList 정렬 정보 목록
   * @return page
   * @apiNote 페이징 처리
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:13:13
   */
  public static <E, Q extends EntityPathBase<E>, S extends SearchExtension<?>> CursorData<E> paging
  (JPAQuery<E> query, Q path, S search, List<OrderSpecifier<?>> orderList) {
    log.info("QueryUtilAware.paging");

    Long cursor = ofNullable(search.getCursor())
        .orElse(LocalDateTime.now()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli());

    final PagingFunctional<E, S> PAGING_FUNCTIONAL = (functionalQuery, functionalSearch) -> {
      PageRequest pageRequest =
          of(functionalSearch.getPage() - 1, functionalSearch.getSize());

      Long totalCount = functionalQuery.select(Wildcard.count).fetchOne();

      functionalQuery.select(path);
      functionalQuery.offset(pageRequest.getOffset())
          .limit(pageRequest.getPageSize());

      if (!isNull(orderList) && !orderList.isEmpty()) {
        functionalQuery.orderBy(orderList.toArray(new OrderSpecifier<?>[0]));
      }

      List<E> result = functionalQuery.fetch();

      return new CursorData<>(result, pageRequest,
          ofNullable(totalCount).orElse(0L), cursor);
    };

    return PAGING_FUNCTIONAL.paging(query, search);
  }

}
