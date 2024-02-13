package run.freshr.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Access 토큰 갱신 response DTO
 *
 * @author 류성재
 * @apiNote Access 토큰 갱신 response DTO
 * @since 2023. 6. 16. 오후 4:50:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {

  /**
   * Access 토큰
   *
   * @apiNote Access 토큰
   * @since 2023. 6. 16. 오후 4:50:10
   */
  private String accessToken;

}
