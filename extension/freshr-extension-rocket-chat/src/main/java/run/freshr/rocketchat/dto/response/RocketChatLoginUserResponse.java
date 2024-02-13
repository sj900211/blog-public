package run.freshr.rocketchat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.rocketchat.data.RocketChatSignData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketChatLoginUserResponse {

  private String status;
  private RocketChatSignData data;

}
