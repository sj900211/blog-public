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
public class RocketChatSetAvatarRequest {

  @NotEmpty
  private String userId;

  @NotEmpty
  private String avatarUrl;

}
