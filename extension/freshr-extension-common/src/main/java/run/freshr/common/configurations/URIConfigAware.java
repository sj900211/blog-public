package run.freshr.common.configurations;

/**
 * URI 정의
 *
 * @author 류성재
 * @apiNote URI 정의<br>
 *          Annotation 에서 문자가 아닌 변수로 사용해서 관리 포인트를 집중하기 위해 정의
 * @since 2023. 6. 16. 오후 1:29:28
 */
public abstract class URIConfigAware {

  public static final String uriDocsAll = "/docs/**"; // API 문서 URI
  public static final String uriFavicon = "/favicon.ico"; // 파비콘 URL

  public static final String uriDocsIndex = "/docs"; // API 문서 URI
  public static final String uriDocsDepth1 = "/docs/{depth1}"; // API 문서 URI
  public static final String uriDocsDepth2 = "/docs/{depth1}/{depth2}"; // API 문서 URI
  public static final String uriDocsDepth3 = "/docs/{depth1}/{depth2}/{depth3}"; // API 문서 URI

  public static final String uriCommonHeartbeat = "/heartbeat"; // 서비스가 실행되었는지 체크하기 위한 URI
  public static final String uriCommonEnum = "/enum";
  public static final String uriCommonEnumPick = "/enum/{pick}";

}
