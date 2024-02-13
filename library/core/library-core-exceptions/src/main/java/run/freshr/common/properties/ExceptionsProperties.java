package run.freshr.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;

/**
 * 예외 속성 정의
 *
 * @author 류성재
 * @apiNote 예외 속성을 정의해서 application.properties 또는 application.yml 에서 사용할 수 있도록 설정
 * @since 2022. 12. 23. 오후 4:23:59
 */
@Data
@ConfigurationProperties("freshr.exceptions")
public class ExceptionsProperties {

  /**
   * success exception 속성 설정
   *
   * @apiNote success exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus successStatus;
  private String successMessage;
  private String successCode;
  /**
   * parameter exception 속성 설정
   *
   * @apiNote parameter exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus parameterStatus;
  private String parameterMessage;
  private String parameterCode;
  /**
   * duplicate exception 속성 설정
   *
   * @apiNote duplicate exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus duplicateStatus;
  private String duplicateMessage;
  private String duplicateCode;
  /**
   * un authenticated exception 속성 설정
   *
   * @apiNote un authenticated exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus unAuthenticatedStatus;
  private String unAuthenticatedMessage;
  private String unAuthenticatedCode;
  /**
   * null pointer exception 속성 설정
   *
   * @apiNote null pointer exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus nullPointerStatus;
  private String nullPointerMessage;
  private String nullPointerCode;
  /**
   * entity not found exception 속성 설정
   *
   * @apiNote entity not found exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus entityNotFoundStatus;
  private String entityNotFoundMessage;
  private String entityNotFoundCode;
  /**
   * io exception 속성 설정
   *
   * @apiNote io exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus ioStatus;
  private String ioMessage;
  private String ioCode;
  /**
   * file size limit exceeded exception 속성 설정
   *
   * @apiNote file size limit exceeded exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus fileSizeLimitExceededStatus;
  private String fileSizeLimitExceededMessage;
  private String fileSizeLimitExceededCode;
  /**
   * json exception 속성 설정
   *
   * @apiNote json exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus jsonStatus;
  private String jsonMessage;
  private String jsonCode;
  /**
   * json processing exception 속성 설정
   *
   * @apiNote json processing exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus jsonProcessingStatus;
  private String jsonProcessingMessage;
  private String jsonProcessingCode;
  /**
   * sql syntax error exception 속성 설정
   *
   * @apiNote sql syntax error exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus sqlSyntaxErrorStatus;
  private String sqlSyntaxErrorMessage;
  private String sqlSyntaxErrorCode;
  /**
   * invalid data access resource usage exception 속성 설정
   *
   * @apiNote invalid data access resource usage exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus invalidDataAccessResourceUsageStatus;
  private String invalidDataAccessResourceUsageMessage;
  private String invalidDataAccessResourceUsageCode;
  /**
   * data integrity violation exception 속성 설정
   *
   * @apiNote data integrity violation exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus dataIntegrityViolationStatus;
  private String dataIntegrityViolationMessage;
  private String dataIntegrityViolationCode;
  /**
   * batch update exception 속성 설정
   *
   * @apiNote batch update exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus batchUpdateStatus;
  private String batchUpdateMessage;
  private String batchUpdateCode;
  /**
   * sql grammar exception 속성 설정
   *
   * @apiNote sql grammar exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus sqlGrammarStatus;
  private String sqlGrammarMessage;
  private String sqlGrammarCode;
  /**
   * constraint violation exception 속성 설정
   *
   * @apiNote constraint violation exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus constraintViolationStatus;
  private String constraintViolationMessage;
  private String constraintViolationCode;
  /**
   * data exception 속성 설정
   *
   * @apiNote data exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus dataStatus;
  private String dataMessage;
  private String dataCode;
  /**
   * access denied exception 속성 설정
   *
   * @apiNote access denied exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus accessDeniedStatus;
  private String accessDeniedMessage;
  private String accessDeniedCode;
  /**
   * illegal state exception 속성 설정
   *
   * @apiNote illegal state exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus illegalStateStatus;
  private String illegalStateMessage;
  private String illegalStateCode;
  /**
   * illegal argument exception 속성 설정
   *
   * @apiNote illegal argument exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus illegalArgumentStatus;
  private String illegalArgumentMessage;
  private String illegalArgumentCode;
  /**
   * jwt exception 속성 설정
   *
   * @apiNote jwt exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus jwtStatus;
  private String jwtMessage;
  private String jwtCode;
  /**
   * expired jwt exception 속성 설정
   *
   * @apiNote expired jwt exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus expiredJwtStatus;
  private String expiredJwtMessage;
  private String expiredJwtCode;
  /**
   * error exception 속성 설정
   *
   * @apiNote error exception 속성 설정
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private HttpStatus errorStatus;
  private String errorMessage;
  private String errorCode;

}
