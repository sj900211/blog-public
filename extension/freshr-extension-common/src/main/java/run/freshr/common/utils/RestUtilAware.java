package run.freshr.common.utils;

import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static java.text.MessageFormat.format;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

import com.querydsl.core.types.Path;
import java.util.HashMap;
import java.util.Optional;
import run.freshr.common.configurations.CustomConfigAware;
import run.freshr.common.data.ExceptionsData;
import run.freshr.common.data.ResponseData;
import run.freshr.common.dto.response.IdResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * 자주 사용하는 공통 기능을 정의
 *
 * @author 류성재
 * @apiNote 자주 사용하는 공통 기능을 정의
 * @since 2023. 6. 16. 오후 1:37:55
 */
@Slf4j
@Component
@RequiredArgsConstructor
public abstract class RestUtilAware {

  private static final ObjectMapper objectMapper;

  private static Environment environment; // Spring 서비스 환경 설정
  private static CustomConfigAware customConfigAware; // 추가 설정
  private static ExceptionsData exceptionsData; // 에러 설정

  /**
   * 기본 DATE 포맷 정의
   *
   * @apiNote 기본 DATE 포맷 정의
   * @since 2023. 6. 16. 오후 1:37:56
   */
  private static final String DATE_FORMAT = "yyyy-MM-dd";
  /**
   * 기본 DATETIME 포맷
   *
   * @apiNote 기본 DATETIME 포맷
   * @since 2023. 6. 16. 오후 1:37:56
   */
  private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  // Object Mapper DATE, DATETIME 포맷 설정
  static {
    objectMapper = new ObjectMapper();

    JavaTimeModule javaTimeModule = new JavaTimeModule();

    javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(ofPattern(DATE_FORMAT)));
    javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(ofPattern(DATE_FORMAT)));
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ofPattern(DATE_TIME_FORMAT)));
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ofPattern(DATE_TIME_FORMAT)));

    objectMapper.registerModule(javaTimeModule);
  }

  @Autowired
  public RestUtilAware(Environment environment, CustomConfigAware customConfigAware,
      ExceptionsData exceptionsData) {
    RestUtilAware.environment = environment;
    RestUtilAware.customConfigAware = customConfigAware;
    RestUtilAware.exceptionsData = exceptionsData;
  }

  /**
   * View class 반환
   *
   * @param modelAndView model and view
   * @param prefix       prefix 문자
   * @param paths        경로 및 파일 이름
   * @return model and view
   * @apiNote View class 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static ModelAndView view(final ModelAndView modelAndView, final String prefix,
      final String... paths) {
    log.info("RestUtilAware.view");

    StringBuilder uri = new StringBuilder(prefix);

    for (String path : paths) {
      uri.append("/").append(path);
    }

    modelAndView.setViewName(uri.toString());

    return modelAndView;
  }

  /**
   * 성공 반환
   *
   * @return response entity
   * @apiNote 성공 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static ResponseEntity<?> ok() {
    log.info("RestUtilAware.ok");

    return ok(exceptionsData.getSuccess().getMessage());
  }

  /**
   * 성공 반환
   *
   * @param message message
   * @return response entity
   * @apiNote 성공 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static ResponseEntity<?> ok(final String message) {
    log.info("RestUtilAware.ok");

    return ok(ResponseData
        .builder()
        .message(ofNullable(message).orElse(exceptionsData.getSuccess().getMessage()))
        .build());
  }

  /**
   * 성공 반환
   *
   * @param <T>  반환 데이터 유형
   * @param data 반환 데이터
   * @return response entity
   * @apiNote 성공 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static <T> ResponseEntity<?> ok(final T data) {
    log.info("RestUtilAware.ok");

    return ok(ResponseData
        .builder()
        .message(exceptionsData.getSuccess().getMessage())
        .data(data)
        .build());
  }

  /**
   * 성공 반환
   *
   * @param <T>  반환 데이터 유형
   * @param list 반환 목록 데이터
   * @return response entity
   * @apiNote 성공 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static <T> ResponseEntity<?> ok(final List<T> list) {
    log.info("RestUtilAware.ok");

    return ok(ResponseData
        .builder()
        .message(exceptionsData.getSuccess().getMessage())
        .list(list)
        .build());
  }

  /**
   * 성공 반환
   *
   * @param <T>  반환 데이터 유형
   * @param page 반환 페이지 데이터
   * @return response entity
   * @apiNote 성공 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static <T> ResponseEntity<?> ok(final Page<T> page) {
    log.info("RestUtilAware.ok");

    return ok(ResponseData
        .builder()
        .message(exceptionsData.getSuccess().getMessage())
        .page(page)
        .build());
  }

  /**
   * 성공 반환
   *
   * @param body 반환 모델
   * @return response entity
   * @apiNote 성공 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static ResponseEntity<?> ok(final ResponseData body) {
    log.info("RestUtilAware.ok");

    return ResponseEntity
        .ok()
        .body(objectMapper.valueToTree(body));
  }

  /**
   * 에러 반환
   *
   * @param httpStatus HTTP Status 코드
   * @param message    메시지
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static ResponseEntity<?> error(final HttpStatus httpStatus, final String message) {
    log.info("RestUtilAware.error");

    return error(
        httpStatus,
        null,
        null,
        ofNullable(message).orElse(exceptionsData.getError().getMessage())
    );
  }

  /**
   * 에러 반환
   *
   * @param message formatting 메시지
   * @param args    formatting 값 목록
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static ResponseEntity<?> error(final String message, final Object[] args) {
    log.info("RestUtilAware.error");

    return error(
        exceptionsData.getError().getHttpStatus(),
        exceptionsData.getError().getHttpStatus().name(),
        null,
        format(ofNullable(message).orElse(exceptionsData.getError().getMessage()), args)
    );
  }

  /**
   * 에러 반환
   *
   * @param exceptions 정의된 에러 유형
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:55
   */
  public static ResponseEntity<?> error(final ExceptionsData.Exceptions exceptions) {
    log.info("RestUtilAware.error");

    return error(exceptions, null, null);
  }

  /**
   * 에러 반환
   *
   * @param exceptions 정의된 에러 유형
   * @param message    메시지
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static ResponseEntity<?> error(final ExceptionsData.Exceptions exceptions,
      final String message) {
    log.info("RestUtilAware.error");

    return error(exceptions, message, null);
  }

  /**
   * 에러 반환
   *
   * @param exceptions 정의된 에러 유형
   * @param message    formatting 메시지
   * @param args       formatting 값 목록
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static ResponseEntity<?> error(final ExceptionsData.Exceptions exceptions,
      final String message, final Object[] args) {
    log.info("RestUtilAware.error");

    return error(
        exceptions.getHttpStatus(),
        UPPER_UNDERSCORE.to(LOWER_HYPHEN, exceptions.getHttpStatus().name()),
        exceptions.getCode(),
        format(ofNullable(message).orElse(exceptions.getMessage()), args)
    );
  }

  /**
   * 에러 반환
   *
   * @param httpStatus HTTP Status 코드
   * @param name       에러 이름
   * @param code       에러 코드
   * @param message    메시지
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static ResponseEntity<?> error(final HttpStatus httpStatus, final String name,
      final String code, final String message) {
    log.info("RestUtilAware.error");

    return ResponseEntity
        .status(httpStatus)
        .body(objectMapper.valueToTree(
            ResponseData
                .builder()
                .name(name)
                .code(code)
                .message(message)
                .build()
        ));
  }

  /**
   * 에러 반환
   *
   * @param bindingResult RequestBody Valid 에러 객체
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static ResponseEntity<?> error(final BindingResult bindingResult) {
    log.info("RestUtilAware.error");

    return ResponseEntity
        .badRequest()
        .body(bindingResult);
  }

  /**
   * 에러 반환
   *
   * @param errors ModelAttribute Valid 에러 객체
   * @return response entity
   * @apiNote 에러 반환
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static ResponseEntity<?> error(final Errors errors) {
    log.info("RestUtilAware.error");

    return ResponseEntity
        .badRequest()
        .body(errors);
  }

  /**
   * valid 에러 항목 추가
   *
   * @param name          에러 필드 이름
   * @param bindingResult RequestBody Valid 에러 객체
   * @apiNote valid 에러 항목 추가 - 잘못된 값
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectWrong(final String name, BindingResult bindingResult) {
    log.info("RestUtilAware.rejectWrong");

    bindingResult.rejectValue(name, "wrong value");
  }

  /**
   * valid 에러 항목 추가
   *
   * @param name          name
   * @param description   description
   * @param bindingResult RequestBody Valid 에러 객체
   * @apiNote valid 에러 항목 추가 - 잘못된 값
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectWrong(final String name, final String description,
      BindingResult bindingResult) {
    log.info("RestUtilAware.rejectWrong");

    bindingResult.rejectValue(name, "wrong value", description);
  }

  /**
   * valid 에러 항목 추가
   *
   * @param name   name
   * @param errors ModelAttribute Valid 에러 객체
   * @apiNote valid 에러 항목 추가 - 잘못된 값
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectWrong(final String name, Errors errors) {
    log.info("RestUtilAware.rejectWrong");

    errors.rejectValue(name, "wrong value");
  }

  /**
   * valid 에러 항목 추가
   *
   * @param name        name
   * @param description description
   * @param errors      errors
   * @apiNote valid 에러 항목 추가 - 잘못된 값
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectWrong(final String name, final String description, Errors errors) {
    log.info("RestUtilAware.rejectWrong");

    errors.rejectValue(name, "wrong value", description);
  }

  /**
   * valid 에러 항목 추가
   *
   * @param bindingResult RequestBody Valid 에러 객체
   * @param names         names
   * @apiNote valid 에러 항목 추가 - 필수 항목
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectRequired(BindingResult bindingResult, final String... names) {
    log.info("RestUtilAware.rejectRequired");

    stream(names).forEach(name -> rejectRequired(name, bindingResult));
  }

  /**
   * valid 에러 항목 추가
   *
   * @param name          name
   * @param bindingResult RequestBody Valid 에러 객체
   * @apiNote valid 에러 항목 추가 - 필수 항목
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectRequired(final String name, BindingResult bindingResult) {
    log.info("RestUtilAware.rejectRequired");

    bindingResult.rejectValue(name, "required value");
  }

  /**
   * valid 에러 항목 추가
   *
   * @param errors ModelAttribute Valid 에러 객체
   * @param names  names
   * @apiNote valid 에러 항목 추가 - 필수 항목
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectRequired(Errors errors, final String... names) {
    log.info("RestUtilAware.rejectRequired");

    stream(names).forEach(name -> rejectRequired(name, errors));
  }

  /**
   * valid 에러 항목 추가
   *
   * @param name   name
   * @param errors ModelAttribute Valid 에러 객체
   * @apiNote valid 에러 항목 추가 - 필수 항목
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectRequired(final String name, Errors errors) {
    log.info("RestUtilAware.rejectRequired");

    errors.rejectValue(name, "required value");
  }

  /**
   * valid 에러 항목 추가
   *
   * @param bindingResult RequestBody Valid 에러 객체
   * @apiNote valid 에러 항목 추가 - 권한
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectAuth(BindingResult bindingResult) {
    log.info("RestUtilAware.rejectAuth");

    bindingResult.rejectValue("UnAuthenticated", "permission denied");
  }

  /**
   * valid 에러 항목 추가
   *
   * @param errors ModelAttribute Valid 에러 객체
   * @apiNote valid 에러 항목 추가 - 권한
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static void rejectAuth(Errors errors) {
    log.info("RestUtilAware.rejectAuth");

    errors.rejectValue("UnAuthenticated", "permission denied");
  }

  /**
   * 설정한 예외 항목 조회
   *
   * @return exceptions
   * @apiNote 설정한 예외 항목 조회
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static ExceptionsData getExceptions() {
    log.info("RestUtilAware.getExceptions");

    return RestUtilAware.exceptionsData;
  }

  /**
   * 실행중인 서비스의 profile 체크
   *
   * @param profile profile 이름
   * @return boolean
   * @apiNote 실행중인 서비스의 profile 체크
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static boolean checkProfile(final String profile) {
    log.info("RestUtilAware.checkProfile");

    return stream(environment.getActiveProfiles())
        .anyMatch(active -> active.equalsIgnoreCase(profile));
  }

  /**
   * 실행중인 서비스의 profile 체크
   *
   * @param profiles profile 이름 목록
   * @return boolean
   * @apiNote 실행중인 서비스의 profile 체크
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static boolean checkProfile(final String... profiles) {
    log.info("RestUtilAware.checkProfile");

    return stream(environment.getActiveProfiles())
        .anyMatch(active -> stream(profiles).anyMatch(active::equalsIgnoreCase));
  }

  /**
   * 추가 설정 조회
   *
   * @return config
   * @apiNote 추가 설정 조회
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:37:56
   */
  public static CustomConfigAware getConfig() {
    log.info("RestUtilAware.getConfig");

    return customConfigAware;
  }

  /**
   * ID 반환.
   *
   * @param <ID> ID 데이터 유형
   * @param id   일련 번호
   * @return id response
   * @apiNote {@link IdResponse} 를 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 10:14:27
   */
  public static <ID> IdResponse<ID> buildId(ID id) {
    return IdResponse.<ID>builder().id(id).build();
  }

  /**
   * 필드 이름 조회
   *
   * @param path path
   * @return string
   * @apiNote QueryDsl Path 클래스를 활용하여 field 이름을 조회
   *          하드코딩을 줄이기 위한 방책
   * @author FreshR
   * @since 2023. 9. 26. 오전 9:33:09
   */
  public static String field(Path<?> path) {
    String qPath = path.toString();
    int qDotPoint = qPath.indexOf(".") + 1;
    String target = qPath.substring(0, qDotPoint);

    return qPath.replace(target, "").replace(")", "");
  }

  public static String field(HashMap<?, ?> map) {
    return Optional.of(map.get("name").toString()).orElse("");
  }

}
