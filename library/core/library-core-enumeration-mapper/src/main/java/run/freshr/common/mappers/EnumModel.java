package run.freshr.common.mappers;

/**
 * 열거형 데이터 모델
 *
 * @author 류성재
 * @apiNote 열거형 데이터 모델<br>
 *          열거형 데이터를 조회할 때 공통된 형태로<br>
 *          사용하기 위한 상속 인터페이스
 * @since 2022. 12. 23. 오후 4:23:59
 */
public interface EnumModel {

  /**
   * 키 조회
   *
   * @return 키 문자
   * @apiNote 열거형 데이터에서 설정한 키 문자를 반환
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  String getKey();

  /**
   * 값 조회
   *
   * @return 값 문자
   * @apiNote 열거형 데이터에서 설정한 값 문자를 반환
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  String getValue();

}
