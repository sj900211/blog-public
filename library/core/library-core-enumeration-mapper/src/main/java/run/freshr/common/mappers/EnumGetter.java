package run.freshr.common.mappers;

import lombok.Getter;

/**
 * 열거형 데이터 반환 객체
 *
 * @author 류성재
 * @apiNote 열거형 데이터를 조회해서 반환할 공통 객체
 * @since 2022. 12. 23. 오후 4:23:59
 */
@Getter
public class EnumGetter {

  /**
   * 키 문자
   *
   * @apiNote EnumModel 을 상속받아 설정한 키 문자
   * @since 2022. 12. 23. 오후 4:23:59
   *
   */
  String key;
  /**
   * 값 문자
   *
   * @apiNote EnumModel 을 상속받아 설정한 값 문자
   * @since 2022. 12. 23. 오후 4:23:59
   *
   */
  String value;

  /**
   * 생성자
   *
   * @param model 열거형 데이터 모델
   * @apiNote 열거형 데이터 모델의 키와 값으로 반환 객체 생성
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public EnumGetter(final EnumModel model) {
    key = model.getKey();
    value = model.getValue();
  }

}
