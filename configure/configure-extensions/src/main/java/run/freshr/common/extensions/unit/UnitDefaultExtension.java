package run.freshr.common.extensions.unit;

/**
 * 기본 Unit 기능 interface
 *
 * @param <E>  Entity
 * @param <ID> ID 데이터 유형
 * @author 류성재
 * @apiNote Unit 공통 기능을 정의
 * @since 2023. 6. 15. 오후 1:31:00
 */
public interface UnitDefaultExtension<E, ID> {

  /**
   * 등록
   *
   * @param entity Entity
   * @return id
   * @apiNote 데이터 생성
   * @author 류성재
   * @since 2023. 6. 15. 오후 1:31:00
   */
  ID create(E entity);

  /**
   * 데이터 존재 여부
   *
   * @param id 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 15. 오후 1:31:00
   */
  Boolean exists(ID id);

  /**
   * 데이터 조회
   *
   * @param id 일련 번호
   * @return e
   * @apiNote 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 1:31:00
   */
  E get(ID id);

}
