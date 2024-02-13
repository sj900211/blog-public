package run.freshr.common.enumerations;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import run.freshr.common.mappers.EnumModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

/**
 * 예외 기본 속성 정의
 *
 * @author 류성재
 * @implNote  예외 처리 기본 속성을 정의
 * @since 2022. 12. 23. 오후 4:23:59
 */
@JsonFormat(shape = OBJECT)
public enum StatusEnum implements EnumModel {

  /**
   * 성공 속성 정의
   *
   * @apiNote 성공 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  SUCCESS(OK, "process success.", "OK"),

  //   ______  __    __       _______.___________.  ______   .___  ___.
  //  /      ||  |  |  |     /       |           | /  __  \  |   \/   |
  // |  ,----'|  |  |  |    |   (----`---|  |----`|  |  |  | |  \  /  |
  // |  |     |  |  |  |     \   \       |  |     |  |  |  | |  |\/|  |
  // |  `----.|  `--'  | .----)   |      |  |     |  `--'  | |  |  |  |
  //  \______| \______/  |_______/       |__|      \______/  |__|  |__|
  // 커스텀 오류 관리

  /**
   * 파라미터 오류 속성 정의
   *
   * @apiNote 파라미터 오류 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  PARAMETER(BAD_REQUEST, "[{0}] Parameter Exception", "EMPTY_PARAMETER"),
  /**
   * 중복 데이터 오류 속성 정의
   *
   * @apiNote 중복 데이터 오류 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  DUPLICATE(BAD_REQUEST, "[{0}] Duplicate Exception", "DATA_DUPLICATE"),

  /**
   * 인증 오류 속성 정의
   *
   * @apiNote 인증 오류 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  UN_AUTHENTICATED(UNAUTHORIZED, "Un Authenticated Exception", "AUTH"),

  //  _______   _______  _______    ___      __    __   __      .___________.
  // |       \ |   ____||   ____|  /   \    |  |  |  | |  |     |           |
  // |  .--.  ||  |__   |  |__    /  ^  \   |  |  |  | |  |     `---|  |----`
  // |  |  |  ||   __|  |   __|  /  /_\  \  |  |  |  | |  |         |  |
  // |  '--'  ||  |____ |  |    /  _____  \ |  `--'  | |  `----.    |  |
  // |_______/ |_______||__|   /__/     \__\ \______/  |_______|    |__|
  // 기본 오류 관리

  /**
   * NullPointerException 속성 정의
   *
   * @apiNote NullPointerException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  NULL_POINTER(INTERNAL_SERVER_ERROR, "Null Pointer Exception", "EMPTY"),
  /**
   * EntityNotFoundException 속성 정의
   *
   * @apiNote EntityNotFoundException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  ENTITY_NOT_FOUND(NOT_FOUND, "Entity Not Found Exception", "EMPTY_DATA"),

  /**
   * IOException 속성 정의
   *
   * @apiNote IOException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  IO(INTERNAL_SERVER_ERROR, "IO Exception", "IO"),
  /**
   * FileSizeLimitExceededException 속성 정의
   *
   * @apiNote FileSizeLimitExceededException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  FILE_SIZE_LIMIT_EXCEEDED(INTERNAL_SERVER_ERROR, "File Size Limit Exceeded Exception", "IO_SIZE"),

  /**
   * JsonProcessingException 속성 정의
   *
   * @apiNote JsonProcessingException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  JSON_PROCESSING(INTERNAL_SERVER_ERROR, "Json Processing Exception", "JSON"),

  /**
   * SQLSyntaxErrorException 속성 정의
   *
   * @apiNote SQLSyntaxErrorException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  SQL_SYNTAX_ERROR(INTERNAL_SERVER_ERROR, "SQL Syntax Error Exception", "SQL"),
  /**
   * InvalidDataAccessResourceUsageException 속성 정의
   *
   * @apiNote InvalidDataAccessResourceUsageException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  INVALID_DATA_ACCESS_RESOURCE_USAGE(INTERNAL_SERVER_ERROR,
      "Invalid Data Access Resource Usage Exception", "SQL_ACCESS"),
  /**
   * DataIntegrityViolationException 속성 정의
   *
   * @apiNote DataIntegrityViolationException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  DATA_INTEGRITY_VIOLATION(INTERNAL_SERVER_ERROR, "Data Integrity Violation Exception", "SQL_DATA"),
  /**
   * BatchUpdateException 속성 정의
   *
   * @apiNote BatchUpdateException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  BATCH_UPDATE(INTERNAL_SERVER_ERROR, "Batch Update Exception", "SQL_UPDATE"),
  /**
   * SQLGrammarException 속성 정의
   *
   * @apiNote SQLGrammarException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  SQL_GRAMMAR(INTERNAL_SERVER_ERROR, "SQL Grammar Exception", "SQL_GRAMMAR"),
  /**
   * ConstraintViolationException 속성 정의
   *
   * @apiNote ConstraintViolationException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  CONSTRAINT_VIOLATION(INTERNAL_SERVER_ERROR, "Constraint Violation Exception", "SQL_VIOLATION"),
  /**
   * DataException 속성 정의
   *
   * @apiNote DataException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  DATA(INTERNAL_SERVER_ERROR, "Data Exception", "DATA"),

  /**
   * AccessDeniedException 속성 정의
   *
   * @apiNote AccessDeniedException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  ACCESS_DENIED(FORBIDDEN, "Access Denied Exception", "ACCESS"),

  /**
   * IllegalStateException 속성 정의
   *
   * @apiNote IllegalStateException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  ILLEGAL_STATE(INTERNAL_SERVER_ERROR, "Illegal State Exception", "ILLEGAL"),
  /**
   * IllegalStateException 속성 정의
   *
   * @apiNote IllegalStateException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  ILLEGAL_ARGUMENT(INTERNAL_SERVER_ERROR, "Illegal Argument Exception", "ILLEGAL_ARGUMENT"),

  /**
   * JwtException 속성 정의
   *
   * @apiNote JwtException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  JWT(INTERNAL_SERVER_ERROR, "Jwt Exception", "TOKEN"),
  /**
   * ExpiredJwtException 속성 정의
   *
   * @apiNote ExpiredJwtException 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  EXPIRED_JWT(INTERNAL_SERVER_ERROR, "Expired JWT Exception", "TOKEN_EXPIRED"),

  /**
   * UNTOUCHABLE 속성 정의
   *
   * @apiNote UNTOUCHABLE 속성 정의
   * @since 2022. 12. 23. 오후 4:23:59
   */
  ERROR(BAD_REQUEST, "System Exception. Call Developer Right Now.", "ERROR");

  /**
   * Http Status 속성
   *
   * @apiNote Http Status 속성
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private final HttpStatus status;
  /**
   * 메시지 속성
   *
   * @apiNote 메시지 속성
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private final String message;
  /**
   * 에러 코드 속성
   *
   * @apiNote 에러 코드 속성
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private final String code;

  /**
   * 생성자
   *
   * @param status  Http Status
   * @param message 메시지
   * @param code    에러 코드
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  StatusEnum(final HttpStatus status, final String message, final String code) {
    this.status = status;
    this.message = message;
    this.code = code;
  }

  /**
   * 열거 데이터 이름 조회
   *
   * @return 열거 데이터 이름
   * @apiNote 열거 데이터 이름 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  @Override
  public String getKey() {
    return this.name();
  }

  /**
   * 열거 데이터 Http Status 조회
   *
   * @return 열거 데이터 Http Status
   * @apiNote 열거 데이터 Http Status 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  @Override
  public String getValue() {
    return status.toString();
  }

  /**
   * 열거 데이터 Http Status 조회
   *
   * @return 열거 데이터 Http Status
   * @apiNote 열거 데이터 Http Status 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public HttpStatus getStatus() {
    return status;
  }

  /**
   * 열거 데이터 메시지 조회
   *
   * @return 열거 데이터 메시지
   * @apiNote 열거 데이터 메시지 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public String getMessage() {
    return message;
  }

  /**
   * 열거 데이터 에러 코드 조회
   *
   * @return 열거 데이터 에러 코드
   * @apiNote 열거 데이터 에러 코드 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public String getCode() {
    return code;
  }

}
