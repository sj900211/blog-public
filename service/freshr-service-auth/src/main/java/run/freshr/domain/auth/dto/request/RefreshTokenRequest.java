package run.freshr.domain.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Access 토큰 갱신 request DTO
 *
 * @author 류성재
 * @apiNote Access 토큰 갱신 request DTO
 * @since 2023. 6. 16. 오후 4:43:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

  /**
   * Access 토큰
   *
   * @apiNote Access 토큰
   * @since 2023. 6. 16. 오후 4:43:01
   */
  @NotEmpty
  private String accessToken;

}
