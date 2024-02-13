package run.freshr.common.extensions.unit;

import run.freshr.common.data.CursorData;
import run.freshr.common.extensions.request.SearchExtension;

/**
 * Paging 조회가 있는 Unit 공통 기능을 정의
 *
 * @param <E>  Entity
 * @param <ID> ID 데이터 유형
 * @param <S>  SearchExtension 을 상속받은 Get Parameter VO
 * @author 류성재
 * @apiNote Paging 조회가 있는 Unit 공통 기능을 정의
 * @since 2023. 6. 15. 오후 4:10:39
 */
public interface UnitPageExtension<E, ID, S extends SearchExtension<ID>>
    extends UnitDefaultExtension<E, ID> {

  /**
   * 페이징 데이터 조회
   *
   * @param search SearchExtension 을 상속받은 Get Parameter VO
   * @return page
   * @apiNote 페이징 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:10:39
   */
  CursorData<E> getPage(S search);

}
