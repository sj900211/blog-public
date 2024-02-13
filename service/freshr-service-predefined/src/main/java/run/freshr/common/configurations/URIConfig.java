package run.freshr.common.configurations;

/**
 * URI 정의
 *
 * @author 류성재
 * @apiNote URI 정의
 * @since 2023. 6. 16. 오후 3:34:43
 */
public class URIConfig extends URIConfigAware {

  public static final String uriAttach = "/attach";
  public static final String uriAttachId = "/attach/{id}";
  public static final String uriAttachIdDownload = "/attach/{id}/download";
  public static final String uriBasicImageType = "/basic-image/{type}";
  public static final String uriHashtag = "/hashtag";

}
