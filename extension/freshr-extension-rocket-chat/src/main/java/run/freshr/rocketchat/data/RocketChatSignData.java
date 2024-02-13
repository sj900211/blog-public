package run.freshr.rocketchat.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketChatSignData {

  private String userId;

  private String authToken;

}
