package run.freshr.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 질의에 대한 논리 데이터 response DTO
 *
 * @author 류성재
 * @apiNote 반환 데이터에 is(Boolean) 만 있는 경우 공통으로 사용하는 Response DTO<br>
 *          자주 사용하는 구조기때문에 공통으로 생성
 * @since 2023. 6. 15. 오후 1:25:23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IsResponse {

  /**
   * Is
   *
   * @apiNote 질의에 대한 논리 데이터
   * @since 2023. 6. 15. 오후 1:25:23
   */
  private Boolean is;

}
