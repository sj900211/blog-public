package run.freshr.common.extensions.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 물리 삭제 response DTO
 *
 * @param <ID> ID 데이터 유형
 * @author 류성재
 * @apiNote 물리 삭제 정책을 가진 데이터의 공통 필드를 정의한 Response
 * @since 2023. 6. 15. 오후 4:10:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePhysicalExtension<ID> {

  /**
   * Id
   *
   * @apiNote 일련 번호
   * @since 2023. 6. 15. 오후 4:10:09
   */
  protected ID id;

  /**
   * Create at
   *
   * @apiNote 등록 날짜 시간
   * @since 2023. 6. 15. 오후 4:10:09
   */
  protected LocalDateTime createAt;

}
