package run.freshr.common.docs;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;

import run.freshr.common.utils.PrintUtil;
import run.freshr.common.utils.PrintUtil.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통 반환 구조를 정의
 *
 * @author 류성재
 * @apiNote 공통 반환 구조를 정의
 * @since 2023. 1. 13. 오전 11:01:21
 */
@Slf4j
public class ResponseDocs {

  /**
   * 공통 반환 구조
   *
   * @return builder
   * @apiNote data 를 사용하는 공통 반환 구조
   * @author 류성재
   * @since 2023. 1. 18. 오후 5:00:03
   */
  public static Builder data() {
    log.info("ResponseDocs.data");

    return PrintUtil
        .builder()

        .prefixOptional()
        .field("message", "결과 메시지", STRING)
        .field("data", "반환 데이터 객체", OBJECT)
        .clearOptional()

        .prefix("data");
  }

  /**
   * 공통 반환 구조
   *
   * @return builder
   * @apiNote list 를 사용하는 공통 반환 구조
   * @author 류성재
   * @since 2023. 1. 18. 오후 5:00:03
   */
  public static Builder list() {
    log.info("ResponseDocs.list");

    return PrintUtil
        .builder()

        .prefixOptional()
        .field("message", "결과 메시지", STRING)
        .field("list", "반환 데이터 목록", ARRAY)
        .clearOptional()

        .prefix("list[]");
  }

  /**
   * 공통 반환 구조
   *
   * @return builder
   * @apiNote page 를 사용하는 공통 반환 구조
   * @author 류성재
   * @since 2023. 1. 18. 오후 5:00:03
   */
  public static Builder page() {
    log.info("ResponseDocs.page");

    return PrintUtil
        .builder()

        .prefixOptional()
        .field("message", "결과 메시지", STRING)
        .field("page", "반환 데이터 객체", OBJECT)
        .clearOptional()

        .prefix("page")

        .field("content", "페이지 데이터 목록", ARRAY)
        .field("totalElements", "총 데이터 수", NUMBER)
        .field("last", "마지막 페이지 여부", BOOLEAN)
        .field("totalPages", "총 페이지 수", NUMBER)
        .field("size", "요청한 페이지 데이터 수", NUMBER)
        .field("number", "요청한 페이지 번호", NUMBER)
        .field("sort", "정렬 관련 데이터 객체", OBJECT)
        .field("sort.sorted", "정렬 관련 데이터 객체", BOOLEAN)
        .field("sort.unsorted", "정렬 관련 데이터 객체", BOOLEAN)
        .field("sort.empty", "정렬 관련 데이터 객체", BOOLEAN)
        .field("numberOfElements", "요청한 페이지의 데이터 수", NUMBER)
        .field("first", "첫 페이지 여부", BOOLEAN)
        .field("empty", "데이터가 비어있는지 여부", BOOLEAN)
        .field("pageable", "페이징 관련 데이터 객체", OBJECT)

        .prefix("page.pageable")

        .field("sort", "페이징 관련 데이터 객체", OBJECT)
        .field("sort.sorted", "페이징 관련 데이터 객체", BOOLEAN)
        .field("sort.unsorted", "페이징 관련 데이터 객체", BOOLEAN)
        .field("sort.empty", "페이징 관련 데이터 객체", BOOLEAN)
        .field("offset", "페이징 관련 데이터 객체", NUMBER)
        .field("pageSize", "페이징 관련 데이터 객체", NUMBER)
        .field("pageNumber", "페이징 관련 데이터 객체", NUMBER)
        .field("unpaged", "페이징 관련 데이터 객체", BOOLEAN)
        .field("paged", "페이징 관련 데이터 객체", BOOLEAN)

        .prefix("page.content[]");
  }

  /**
   * 공통 Cursor Data 반환 구조
   *
   * @return builder
   * @apiNote cursor data 를 사용하는 공통 반환 구조
   * @author 류성재
   * @since 2023. 1. 18. 오후 5:00:03
   */
  public static Builder cursor() {
    log.info("ResponseDocs.cursor");

    return PrintUtil
        .builder()

        .prefixOptional()
        .field("message", "결과 메시지", STRING)
        .field("page", "반환 데이터 객체", OBJECT)
        .clearOptional()

        .prefix("page")

        .field("cursor", "커서 데이터", NUMBER)
        .field("content", "페이지 데이터 목록", ARRAY)
        .field("totalElements", "총 데이터 수", NUMBER)
        .field("last", "마지막 페이지 여부", BOOLEAN)
        .field("totalPages", "총 페이지 수", NUMBER)
        .field("size", "요청한 페이지 데이터 수", NUMBER)
        .field("number", "요청한 페이지 번호", NUMBER)
        .field("sort", "정렬 관련 데이터 객체", OBJECT)
        .field("sort.sorted", "정렬 관련 데이터 객체", BOOLEAN)
        .field("sort.unsorted", "정렬 관련 데이터 객체", BOOLEAN)
        .field("sort.empty", "정렬 관련 데이터 객체", BOOLEAN)
        .field("numberOfElements", "요청한 페이지의 데이터 수", NUMBER)
        .field("first", "첫 페이지 여부", BOOLEAN)
        .field("empty", "데이터가 비어있는지 여부", BOOLEAN)
        .field("pageable", "페이징 관련 데이터 객체", OBJECT)

        .prefix("page.pageable")

        .field("sort", "페이징 관련 데이터 객체", OBJECT)
        .field("sort.sorted", "페이징 관련 데이터 객체", BOOLEAN)
        .field("sort.unsorted", "페이징 관련 데이터 객체", BOOLEAN)
        .field("sort.empty", "페이징 관련 데이터 객체", BOOLEAN)
        .field("offset", "페이징 관련 데이터 객체", NUMBER)
        .field("pageSize", "페이징 관련 데이터 객체", NUMBER)
        .field("pageNumber", "페이징 관련 데이터 객체", NUMBER)
        .field("unpaged", "페이징 관련 데이터 객체", BOOLEAN)
        .field("paged", "페이징 관련 데이터 객체", BOOLEAN)

        .prefix("page.content[]");
  }

}
