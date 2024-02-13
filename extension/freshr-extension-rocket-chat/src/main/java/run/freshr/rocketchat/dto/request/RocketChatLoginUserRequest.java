package run.freshr.rocketchat.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketChatLoginUserRequest {

  @NotEmpty
  private String username;

  @NotEmpty
  private String password;

}
