package run.freshr.common.utils;

/**
 * 테스트 전역 변수 관리
 *
 * @author 류성재
 * @apiNote 테스트를 실행 중에 사용할 전역 변수 관리
 * @since 2023. 6. 19. 오전 10:27:00
 */
public class ThreadUtil {

  public static ThreadLocal<String> threadAccess = new ThreadLocal<>(); // ACCESS TOKEN
  public static ThreadLocal<String> threadRefresh = new ThreadLocal<>(); // REFRESH TOKEN
  public static ThreadLocal<String> threadPublicKey = new ThreadLocal<>(); // RSA PUBLIC KEY

}
