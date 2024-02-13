package run.freshr.common.configurations;

/**
 * URI 정의
 *
 * @author 류성재
 * @apiNote URI 정의
 * @since 2023. 6. 16. 오후 3:34:43
 */
public class URIConfig extends URIConfigAware {

  public static final String uriAccount = "/";
  public static final String uriAccountJoin = "/join";
  public static final String uriAccountExistsUsername = "/exists/username";
  public static final String uriAccountExistsNickname = "/exists/nickname";
  public static final String uriAccountId = "/{id}";
  public static final String uriAccountFollowing = "/following";
  public static final String uriAccountFollower = "/follower";
  public static final String uriAccountIdFollow = "/{id}/follow";
  public static final String uriAccountIdFollowExists = "/{id}/follow/exists";
  public static final String uriAccountNotification = "/notification";
  public static final String uriAccountNotificationType = "/notification/{type}";

}
