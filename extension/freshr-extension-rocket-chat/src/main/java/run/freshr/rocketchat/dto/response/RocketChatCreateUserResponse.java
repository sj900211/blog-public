package run.freshr.rocketchat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.rocketchat.data.RocketChatUserData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketChatCreateUserResponse {

  private RocketChatUserData user;
  private Boolean success;

}
