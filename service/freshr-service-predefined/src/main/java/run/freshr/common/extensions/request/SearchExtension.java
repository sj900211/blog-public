package run.freshr.common.extensions.request;

import static java.util.Objects.isNull;

import run.freshr.common.annotations.SearchClass;
import run.freshr.common.annotations.SearchComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Query String Parameter 공통 VO
 *
 * @param <ID> ID 데이터 유형
 * @author 류성재
 * @apiNote Query String Parameter 공통 VO<br>
 *          멤버 변수에 대한 설명은 @SearchComment 기능으로 대체
 * @since 2023. 6. 15. 오후 1:28:06
 */
@Data
@SearchClass(base = true, extend = false)
public class SearchExtension<ID> {

  @SearchComment("페이지 수 [0 부터 시작]")
  protected Integer page;
  @SearchComment("페이지 데이터 수")
  protected Integer size;

  @SearchComment("일련 번호")
  protected ID id;

  @SearchComment("커서")
  protected Long cursor;

  @SearchComment("작성자 일련 번호")
  protected Long creator;
  @SearchComment("마지막 수정자 일련 번호")
  protected Long updater;

  @SearchComment("사용 여부")
  protected Boolean use;

  @JsonProperty("search-type")
  @SearchComment("검색 유형")
  protected String searchType;

  @SearchComment("자연어 검색 대상")
  protected String key;
  @SearchComment("자연어 검색어")
  protected String word;

  @JsonProperty("order-type")
  @SearchComment("정렬 대상")
  protected String orderType;
  @JsonProperty("order-by")
  @SearchComment("정렬 유형")
  protected String orderBy;

  @JsonProperty("date-search-type")
  @SearchComment("날짜 검색 유형")
  protected String dateSearchType;
  @JsonProperty("start-date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("시작 날짜")
  protected LocalDate startDate;
  @JsonProperty("end-date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("종료 날짜")
  protected LocalDate endDate;
  @JsonProperty("start-datetime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @SearchComment("시작 날짜 시간")
  protected LocalDateTime startDatetime;
  @JsonProperty("end-datetime")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @SearchComment("종료 날짜 시간")
  protected LocalDateTime endDatetime;

  public LocalDateTime getCursorAt() {
    LocalDateTime cursorAt;

    if (!isNull(getCursor())) {
      cursorAt = Instant.ofEpochMilli(cursor)
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    } else {
      cursorAt = LocalDateTime.now();
    }

    return cursorAt;
  }

}
