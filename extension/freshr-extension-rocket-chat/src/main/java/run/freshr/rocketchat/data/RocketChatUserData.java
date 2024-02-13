package run.freshr.rocketchat.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketChatUserData {

  @JsonProperty("_id")
  private String id;

  private Boolean active;

  private String status;

  private String type;

  private List<String> roles;

  private String username;

  private String name;

  private String nameInsensitive;

  private List<RocketChatEmailData> emails;

}
