package run.freshr.common.configurations;

import static java.util.Optional.ofNullable;

import run.freshr.common.data.ExceptionsData;
import run.freshr.common.enumerations.StatusEnum;
import run.freshr.common.properties.ExceptionsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 예외 설정
 *
 * @author 류성재
 * @apiNote application.properties 와 application.yml 에서 설정한 값과<br>
 *          기본 설정 값으로 예외 데이터 객체 설정
 * @since 2022. 12. 23. 오후 4:23:59
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ExceptionsProperties.class)
public class ExceptionsConfiguration {

  /**
   * 예외 데이터 객체 설정
   *
   * @param properties application.properties 와 application.yml 에서 설정한 값
   * @return 예외 데이터 객체
   * @apiNote application.properties 와 application.yml 에서 설정한 값과<br>
   *          기본 설정 값으로 예외 데이터 객체 설정
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  @Bean
  @ConditionalOnMissingBean
  public ExceptionsData exceptionsData(ExceptionsProperties properties) {
    return ExceptionsData
        .builder()
        .success(
            setExceptions(
                properties.getSuccessStatus(),
                properties.getSuccessMessage(),
                properties.getSuccessCode(),
                StatusEnum.SUCCESS
            )).parameter(
            setExceptions(
                properties.getParameterStatus(),
                properties.getParameterMessage(),
                properties.getParameterCode(),
                StatusEnum.PARAMETER
            )).duplicate(
            setExceptions(
                properties.getDuplicateStatus(),
                properties.getDuplicateMessage(),
                properties.getDuplicateCode(),
                StatusEnum.DUPLICATE
            )).unAuthenticated(
            setExceptions(
                properties.getUnAuthenticatedStatus(),
                properties.getUnAuthenticatedMessage(),
                properties.getUnAuthenticatedCode(),
                StatusEnum.UN_AUTHENTICATED
            )).nullPointer(
            setExceptions(
                properties.getNullPointerStatus(),
                properties.getNullPointerMessage(),
                properties.getNullPointerCode(),
                StatusEnum.NULL_POINTER
            )).entityNotFound(
            setExceptions(
                properties.getEntityNotFoundStatus(),
                properties.getEntityNotFoundMessage(),
                properties.getEntityNotFoundCode(),
                StatusEnum.ENTITY_NOT_FOUND
            )).io(
            setExceptions(
                properties.getIoStatus(),
                properties.getIoMessage(),
                properties.getIoCode(),
                StatusEnum.IO
            )).fileSizeLimitExceeded(
            setExceptions(
                properties.getFileSizeLimitExceededStatus(),
                properties.getFileSizeLimitExceededMessage(),
                properties.getFileSizeLimitExceededCode(),
                StatusEnum.FILE_SIZE_LIMIT_EXCEEDED
            )).jsonProcessing(
            setExceptions(
                properties.getJsonProcessingStatus(),
                properties.getJsonProcessingMessage(),
                properties.getJsonProcessingCode(),
                StatusEnum.JSON_PROCESSING
            )).sqlSyntaxError(
            setExceptions(
                properties.getSqlSyntaxErrorStatus(),
                properties.getSqlSyntaxErrorMessage(),
                properties.getSqlSyntaxErrorCode(),
                StatusEnum.SQL_SYNTAX_ERROR
            )).invalidDataAccessResourceUsage(
            setExceptions(
                properties.getInvalidDataAccessResourceUsageStatus(),
                properties.getInvalidDataAccessResourceUsageMessage(),
                properties.getInvalidDataAccessResourceUsageCode(),
                StatusEnum.INVALID_DATA_ACCESS_RESOURCE_USAGE
            )).dataIntegrityViolation(
            setExceptions(
                properties.getDataIntegrityViolationStatus(),
                properties.getDataIntegrityViolationMessage(),
                properties.getDataIntegrityViolationCode(),
                StatusEnum.DATA_INTEGRITY_VIOLATION
            )).batchUpdate(
            setExceptions(
                properties.getBatchUpdateStatus(),
                properties.getBatchUpdateMessage(),
                properties.getBatchUpdateCode(),
                StatusEnum.BATCH_UPDATE
            )).sqlGrammar(
            setExceptions(
                properties.getSqlGrammarStatus(),
                properties.getSqlGrammarMessage(),
                properties.getSqlGrammarCode(),
                StatusEnum.SQL_GRAMMAR
            )).constraintViolation(
            setExceptions(
                properties.getConstraintViolationStatus(),
                properties.getConstraintViolationMessage(),
                properties.getConstraintViolationCode(),
                StatusEnum.CONSTRAINT_VIOLATION
            )).data(
            setExceptions(
                properties.getDataStatus(),
                properties.getDataMessage(),
                properties.getDataCode(),
                StatusEnum.DATA
            )).accessDenied(
            setExceptions(
                properties.getAccessDeniedStatus(),
                properties.getAccessDeniedMessage(),
                properties.getAccessDeniedCode(),
                StatusEnum.ACCESS_DENIED
            )).illegalState(
            setExceptions(
                properties.getIllegalStateStatus(),
                properties.getIllegalStateMessage(),
                properties.getIllegalStateCode(),
                StatusEnum.ILLEGAL_STATE
            )).illegalArgument(
            setExceptions(
                properties.getIllegalArgumentStatus(),
                properties.getIllegalArgumentMessage(),
                properties.getIllegalArgumentCode(),
                StatusEnum.ILLEGAL_ARGUMENT
            )).jwt(
            setExceptions(
                properties.getJwtStatus(),
                properties.getJwtMessage(),
                properties.getJwtCode(),
                StatusEnum.JWT
            )).expiredJwt(
            setExceptions(
                properties.getExpiredJwtStatus(),
                properties.getExpiredJwtMessage(),
                properties.getExpiredJwtCode(),
                StatusEnum.EXPIRED_JWT
            )).error(
            setExceptions(
                properties.getErrorStatus(),
                properties.getErrorMessage(),
                properties.getErrorCode(),
                StatusEnum.ERROR
            )).build();
  }

  /**
   * 예외 데이터 설정
   *
   * @param status     Http Status
   * @param message    메시지
   * @param code       에러 코드
   * @param statusEnum 예외 열거 데이터
   * @return the exceptions
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  private ExceptionsData.Exceptions setExceptions(HttpStatus status, String message,
      String code, StatusEnum statusEnum) {
    return ExceptionsData.Exceptions
        .builder()
        .httpStatus(ofNullable(status).orElse(statusEnum.getStatus()))
        .message(ofNullable(message).orElse(statusEnum.getMessage()))
        .code(ofNullable(code).orElse(statusEnum.getCode()))
        .build();
  }

}
