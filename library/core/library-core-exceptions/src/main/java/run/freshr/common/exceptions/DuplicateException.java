package run.freshr.common.exceptions;

import static run.freshr.common.enumerations.StatusEnum.DUPLICATE;

/**
 * 중복 에러
 *
 * @author 류성재
 * @apiNote 중복 에러 정의
 * @since 2022. 12. 23. 오후 4:23:59
 */
public class DuplicateException extends RuntimeException {

  /**
   * 기본 메시지
   *
   * @apiNote 기본 메시지 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private String message = DUPLICATE.getMessage();

  /**
   * 생성자
   *
   * @apiNote 기본 생성자
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public DuplicateException() {
  }

  /**
   * 생성자
   *
   * @param message 메시지
   * @apiNote 메시지 생성자
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public DuplicateException(String message) {
    this.message = message;
  }

}
