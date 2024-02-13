package run.freshr.domain.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RSA 암호화 조회 request DTO
 *
 * @author 류성재
 * @apiNote RSA 암호화 조회 request DTO
 * @since 2023. 6. 16. 오후 4:41:53
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncryptRequest {

  /**
   * RSA 공개 키
   *
   * @apiNote RSA 공개 키
   * @since 2023. 6. 16. 오후 4:41:53
   */
  @NotEmpty
  private String rsa;

  /**
   * 암호화할 평문
   *
   * @apiNote 암호화할 평문
   * @since 2023. 6. 16. 오후 4:41:53
   */
  @NotEmpty
  private String plain;

}
