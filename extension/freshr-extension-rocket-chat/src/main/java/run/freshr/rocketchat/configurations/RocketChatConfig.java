package run.freshr.rocketchat.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class RocketChatConfig {

  @Value("${freshr.rocket-chat.host}")
  private String host;

  @Value("${freshr.rocket-chat.manager.username}")
  private String managerUsername;

  @Value("${freshr.rocket-chat.manager.password}")
  private String managerPassword;

  public static final String apiUsersLogin = "/api/v1/login";
  public static final String apiUsersCreate = "/api/v1/users.create";
  public static final String apiUsersList = "/api/v1/users.list";
  public static final String apiUsersInfo = "/api/v1/users.info";
  public static final String apiUsersUpdate = "/api/v1/users.update";
  public static final String apiUsersDelete = "/api/v1/users.delete";
  public static final String apiUsersSetAvatar = "/api/v1/users.setAvatar";
  public static final String apiUsersGetAvatar = "/api/v1/users.getAvatar";
  public static final String apiUsersCreateToken = "/api/v1/users.createToken";
  public static final String apiUsersLogoutOtherClients = "/api/v1/users.logoutOtherClients";

}
