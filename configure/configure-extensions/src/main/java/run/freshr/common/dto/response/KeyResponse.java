package run.freshr.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Key response DTO
 *
 * @param <KEY> Key 데이터 유형
 * @author 류성재
 * @apiNote 반환 데이터에 key(String) 만 있는 경우 공통으로 사용하는 Response DTO<br>
 *          자주 사용하는 구조기때문에 공통으로 생성
 * @since 2023. 6. 15. 오후 1:26:19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyResponse<KEY> {

  /**
   * Key
   *
   * @apiNote 키
   * @since 2023. 6. 15. 오후 1:26:19
   */
  private KEY key;

}
