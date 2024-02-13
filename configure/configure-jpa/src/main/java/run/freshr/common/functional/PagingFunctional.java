package run.freshr.common.functional;

import run.freshr.common.data.CursorData;
import run.freshr.common.extensions.request.SearchExtension;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * 페이징 functional interface
 *
 * @param <E> Entity
 * @param <S> SearchExtension 을 상속받은 Get Parameter VO
 * @author 류성재
 * @apiNote 페이징 함수형 인터페이스 정의
 * @since 2023. 6. 15. 오후 4:11:11
 */
@FunctionalInterface
public interface PagingFunctional<E, S extends SearchExtension<?>> {

  /**
   * 페이징 처리
   *
   * @param query  JPA Query
   * @param search SearchExtension 을 상속받은 Get Parameter VO
   * @return page
   * @apiNote 데이터 페이징 처리
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:11:11
   */
  CursorData<E> paging(JPAQuery<E> query, S search);

}
