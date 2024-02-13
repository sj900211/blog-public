package run.freshr.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인 response DTO
 *
 * @author 류성재
 * @apiNote 로그인 response DTO
 * @since 2023. 6. 16. 오후 4:51:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

  /**
   * Access 토큰
   *
   * @apiNote Access 토큰
   * @since 2023. 6. 16. 오후 4:51:24
   */
  private String accessToken;

  /**
   * Refresh 토큰
   *
   * @apiNote Refresh 토큰
   * @since 2023. 6. 16. 오후 4:51:24
   */
  private String refreshToken;

}
