package run.freshr.common.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 예외 데이터 모델
 *
 * @author 류성재
 * @apiNote 예외 데이터 모델
 * @since 2022. 12. 23. 오후 4:23:59
 */
@Data
@Builder
public class ExceptionsData {

  /**
   * 성공 데이터
   *
   * @apiNote 성공 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions success;
  /**
   * Parameter 예외 데이터
   *
   * @apiNote Parameter 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions parameter;
  /**
   * Duplicate 예외 데이터
   *
   * @apiNote Duplicate 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions duplicate;
  /**
   * Un authenticated 예외 데이터
   *
   * @apiNote Un authenticated 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions unAuthenticated;
  /**
   * Null pointer 예외 데이터
   *
   * @apiNote Null pointer 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions nullPointer;
  /**
   * Entity not found 예외 데이터
   *
   * @apiNote Entity not found 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions entityNotFound;
  /**
   * Io 예외 데이터
   *
   * @apiNote Io 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions io;
  /**
   * File size limit exceeded 예외 데이터
   *
   * @apiNote File size limit exceeded 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions fileSizeLimitExceeded;
  /**
   * Json 예외 데이터
   *
   * @apiNote Json 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions json;
  /**
   * Json processing 예외 데이터
   *
   * @apiNote Json processing 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions jsonProcessing;
  /**
   * Sql syntax error 예외 데이터
   *
   * @apiNote Sql syntax error 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions sqlSyntaxError;
  /**
   * Invalid data access resource usage 예외 데이터
   *
   * @apiNote Invalid data access resource usage 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions invalidDataAccessResourceUsage;
  /**
   * Data integrity violation 예외 데이터
   *
   * @apiNote Data integrity violation 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions dataIntegrityViolation;
  /**
   * Batch update 예외 데이터
   *
   * @apiNote Batch update 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions batchUpdate;
  /**
   * Sql grammar 예외 데이터
   *
   * @apiNote Sql grammar 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions sqlGrammar;
  /**
   * Constraint violation 예외 데이터
   *
   * @apiNote Constraint violation 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions constraintViolation;
  /**
   * Data 예외 데이터
   *
   * @apiNote Data 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions data;
  /**
   * Access denied 예외 데이터
   *
   * @apiNote Access denied 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions accessDenied;
  /**
   * Illegal state 예외 데이터
   *
   * @apiNote Illegal state 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions illegalState;
  /**
   * Illegal argument 예외 데이터
   *
   * @apiNote Illegal argument 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions illegalArgument;
  /**
   * Jwt 예외 데이터
   *
   * @apiNote Jwt 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions jwt;
  /**
   * Expired jwt 예외 데이터
   *
   * @apiNote Expired jwt 예외 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions expiredJwt;
  /**
   * 에러 데이터
   *
   * @apiNote 에러 데이터
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private Exceptions error;

  /**
   * 데이터 모델
   *
   * @author 류성재
   * @apiNote 데이터 모델
   * @since 2022. 12. 23. 오후 4:23:59
   */
  @Data
  @Builder
  public static class Exceptions {

    /**
     * Http Status
     *
     * @apiNote Http Status
     * @since 2022. 12. 23. 오후 4:23:59
     */
    private HttpStatus httpStatus;
    /**
     * 메시지
     *
     * @apiNote 메시지
     * @since 2022. 12. 23. 오후 4:23:59
     */
    private String message;
    /**
     * 에러 코드
     *
     * @apiNote 에러 코드
     * @since 2022. 12. 23. 오후 4:23:59
     */
    private String code;
  }

}
