package run.freshr.rocketchat.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketChatEmailData {

  private String address;

  private Boolean verified;

}
