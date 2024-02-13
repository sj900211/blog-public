package run.freshr.common.functional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringPath;
import java.util.List;

/**
 * 자연어 검색 functional interface
 *
 * @author 류성재
 * @apiNote 자연어 검색 함수형 인터페이스 정의
 * @since 2023. 6. 15. 오후 4:12:44
 */
@FunctionalInterface
public interface SearchKeywordFunctional {

  /**
   * 자연어 검색
   *
   * @param word  검색어
   * @param paths 검색 대상의 QueryDsl Path 목록
   * @return boolean builder
   * @apiNote 자연어 검색
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:12:44
   */
  BooleanBuilder search(String word, List<StringPath> paths);

}
