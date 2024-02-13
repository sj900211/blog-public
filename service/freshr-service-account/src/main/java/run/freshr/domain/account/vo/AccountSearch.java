package run.freshr.domain.account.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import run.freshr.common.annotations.SearchClass;
import run.freshr.common.annotations.SearchComment;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.request.SearchExtension;
import run.freshr.domain.account.enumerations.AccountStatus;

@Data
@SearchClass
@EqualsAndHashCode(callSuper = true)
public class AccountSearch extends SearchExtension<Long> {

  @SearchComment("상태")
  private AccountStatus status;

  @SearchComment("성별")
  private Gender gender;

  @JsonProperty("birth-start-date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("생일 검색 시작 날짜")
  private LocalDate birthStartDate;

  @JsonProperty("birth-end-date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @SearchComment("생일 검색 종료 날짜")
  private LocalDate birthEndDate;

}
