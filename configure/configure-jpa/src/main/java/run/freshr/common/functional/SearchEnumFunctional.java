package run.freshr.common.functional;

import run.freshr.common.extensions.enumerations.SearchEnumExtension;
import com.querydsl.core.BooleanBuilder;

/**
 * 자연어 검색 functional interface
 *
 * @param <E> SearchEnumExtension 상속받은 검색 유형 Enum
 * @author 류성재
 * @apiNote SearchEnumExtension 을 사용한 자연어 검색 함수형 인터페이스 정의
 * @since 2023. 6. 15. 오후 4:11:39
 */
@FunctionalInterface
public interface SearchEnumFunctional<E extends SearchEnumExtension> {

  /**
   * 자연어 검색
   *
   * @param word        검색어
   * @param enumeration SearchEnumExtension 상속받은 검색 유형 Enum
   * @return boolean builder
   * @apiNote 자연어 검색
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:11:39
   */
  BooleanBuilder search(String word, E enumeration);

}
