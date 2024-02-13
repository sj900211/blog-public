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
public class RocketChatUpdateUserData {

  @NotEmpty
  private String email;

  @NotEmpty
  private String name;

  @NotEmpty
  private String password;

}
