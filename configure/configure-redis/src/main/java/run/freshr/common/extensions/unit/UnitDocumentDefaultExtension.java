package run.freshr.common.extensions.unit;

/**
 * NoSQL Unit 공통 기능을 정의
 *
 * @param <D>  Document
 * @param <ID> ID 데이터 유형
 * @author 류성재
 * @apiNote NoSQL Unit 공통 기능을 정의
 * @since 2023. 6. 15. 오후 4:52:10
 */
public interface UnitDocumentDefaultExtension<D, ID> {

  /**
   * 저장
   *
   * @param document Document
   * @apiNote 저장
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:52:10
   */
  void save(D document);

  /**
   * 데이터 존재 여부
   *
   * @param id 일련 번호
   * @return boolean
   * @apiNote 데이터 존재 여부
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:52:10
   */
  Boolean exists(ID id);

  /**
   * 데이터 조회
   *
   * @param id 일련 번호
   * @return d
   * @apiNote 데이터 조회
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:52:10
   */
  D get(ID id);

  /**
   * 삭제
   *
   * @param id 일련 번호
   * @apiNote 삭제
   * @author 류성재
   * @since 2023. 6. 15. 오후 4:52:10
   */
  void delete(ID id);

}
