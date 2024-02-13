package run.freshr.common.extensions.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Auditor 정보를 갖는 논리 삭제 response DTO
 *
 * @param <ID> ID 데이터 유형
 * @param <A>  Auditor 데이터 유형
 * @author 류성재
 * @apiNote 논리 삭제 정책을 가진 데이터의 공통 필드를 정의한 Response
 * @since 2023. 6. 15. 오후 4:06:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuditLogicalExtension<ID, A> {

  /**
   * Id
   *
   * @apiNote 일련 번호
   * @since 2023. 6. 15. 오후 4:06:51
   */
  protected ID id;

  /**
   * Creator
   *
   * @apiNote 등록자 정보
   * @since 2023. 6. 15. 오후 4:06:51
   */
  protected A creator;

  /**
   * Create at
   *
   * @apiNote 등록 날짜 시간
   * @since 2023. 6. 15. 오후 4:06:51
   */
  protected LocalDateTime createAt;

  /**
   * Updater
   *
   * @apiNote 마지막 수정자 정보
   * @since 2023. 6. 15. 오후 4:06:52
   */
  protected A updater;

  /**
   * Update at
   *
   * @apiNote 마지막 수정 날짜 시간
   * @since 2023. 6. 15. 오후 4:06:52
   */
  protected LocalDateTime updateAt;

}
