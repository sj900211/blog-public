package run.freshr.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RSA 암호화 조회 response DTO
 *
 * @author 류성재
 * @apiNote RSA 암호화 조회 response DTO
 * @since 2023. 6. 16. 오후 4:50:45
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncryptResponse {

  /**
   * 암호화된 문자
   *
   * @apiNote 암호화된 문자
   * @since 2023. 6. 16. 오후 4:50:45
   */
  private String encrypt;

}
