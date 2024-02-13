package run.freshr.rocketchat.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketChatUpdateUserRequest {

  @NotEmpty
  private String userId;

  @NotNull
  private RocketChatUpdateUserData data;

}
