package run.freshr.common.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PK & 정렬 순서 request DTO
 *
 * @param <ID> ID 데이터 유형
 * @author 류성재
 * @apiNote 요청 데이터에 id 와 sort 가 있는 경우 공통으로 사용하는 Request DTO<br>
 *          자주 사용하는 구조기때문에 공통으로 생성
 * @since 2023. 6. 15. 오후 1:20:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdOrderRequest<ID> {

  /**
   * Id
   *
   * @apiNote 일련 번호
   * @since 2023. 6. 15. 오후 1:20:40
   */
  @NotNull
  private ID id;

  /**
   * Sort
   *
   * @apiNote 정렬 순서
   * @since 2023. 6. 15. 오후 1:20:40
   */
  @NotNull
  @Size(min = 1)
  private Integer sort;

}
