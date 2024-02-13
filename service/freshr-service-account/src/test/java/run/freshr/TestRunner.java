package run.freshr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import run.freshr.service.TestService;

/**
 * Test runner.
 *
 * @author FreshR
 * @apiNote Application Run 마지막에 동작하는 Class<br>
 * Test 코드가 실행되기 전에 동작한다.
 * @since 2023. 1. 13. 오전 11:25:09
 */
@Slf4j
@Component
@Profile("test")
public class TestRunner implements ApplicationRunner {

  public static long managerMajorId;
  public static long managerMinorId;
  public static long userId;
  public static List<Long> managerMinorIdList = new ArrayList<>();
  public static List<Long> userIdList = new ArrayList<>();
  public static List<Long> attachIdList = new ArrayList<>();
  public static List<Long> basicIdList = new ArrayList<>();
  public static List<String> hashtagIdList = new ArrayList<>();
  public static List<Long> blogIdList = new ArrayList<>();
  public static List<Long> postIdList = new ArrayList<>();
  public static List<Long> channelIdList = new ArrayList<>();

  @Autowired
  private TestService service;

  /**
   * 테스트 Runner
   *
   * @param args args
   * @apiNote Test 코드는 크게 Given, When, Then 영역으로 나눌 수 있다.<br>
   *          Given 영역은 테스트 데이터를 설정하는 부분인데<br>
   *          Entity 구조가 복잡해질수록, 참조하는 데이터가 많아질수록<br>
   *          Given 영역 코드가 점점 많아진다.<br>
   *          그리고 테스트마다 데이터를 설정하기 때문에 중복 코드도 많아진다.<br>
   *          그러다보니 관리 포인트가 점점 증가하게 되었다.<br>
   *          이 부분을 해결하고자 테스트 코드가 실행하기 전에 기본적인 데이터를 설정한다.<br>
   *          Given 영역은 테스트에 따라 필요하면 생성을 할 수 있고<br>
   *          기본적으로는 각 Entity 에 페이징이 가능한 수의 데이터를 설정해서<br>
   *          Given 영역이 대부분의 Test 코드에서 필요없게 되었다.
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:26:09
   */
  @Override
  public void run(ApplicationArguments args) {
    log.debug(" _______       ___   .___________.    ___         .______       _______     _______. _______ .___________.");
    log.debug("|       \\     /   \\  |           |   /   \\        |   _  \\     |   ____|   /       ||   ____||           |");
    log.debug("|  .--.  |   /  ^  \\ `---|  |----`  /  ^  \\       |  |_)  |    |  |__     |   (----`|  |__   `---|  |----`");
    log.debug("|  |  |  |  /  /_\\  \\    |  |      /  /_\\  \\      |      /     |   __|     \\   \\    |   __|      |  |     ");
    log.debug("|  '--'  | /  _____  \\   |  |     /  _____  \\     |  |\\  \\----.|  |____.----)   |   |  |____     |  |     ");
    log.debug("|_______/ /__/     \\__\\  |__|    /__/     \\__\\    | _| `._____||_______|_______/    |_______|    |__|     ");

    /*
     * 테스트 코드를 실행할 때 Java 코드로 더미데이터를 생성하고 싶었으나
     * 데이터가 1:N > 1:N > 1:N > 1:N 과 같은 구조에서 유형마다 페이징 처리가 가능하도록
     * 더미 데이터를 생성하니 로그 성격의 테이블에 백 만 건이 훌쩍 넘는 양의 데이터가 생성되었다.
     * 그리고 Java 코드 실행 시간은 약 50 분, SQL 스크립트 실행은 약 7 분이 걸렸다.
     * Java 는 여기서 바로 포기했다...
     * SQL 은 희망이 보여 전체적인 데이터 기준을 최소 15 에서 10 으로 줄이니
     * 1 분 이내에 더미데이터 생성이 완료되었다.
     * 그래서 현재 테스트 코드가 실행될 때 SQL 스크립트가 동작하도록 설정이 되어 있다.
     * 하지만 이 방법은 Entity 가 수정되면 SQL 스크립트도 수정해야하기 때문에
     * 번거롭다고 생각한다.
     * 다른 좋은 방법이 있는지 모색해봐야겠다.
     */

    managerMajorId = 1;
    managerMinorId = 2;
    userId = 12;
    managerMinorIdList = LongStream.range(2, 21).boxed().filter(item -> item % 2 == 0).toList();
    userIdList = LongStream.range(2, 21).boxed().filter(item -> item % 2 != 0).toList();
    hashtagIdList = List.of("hashtag001", "hashtag002", "hashtag003", "hashtag004", "hashtag005",
        "hashtag006", "hashtag007", "hashtag008", "hashtag009", "hashtag010");
    attachIdList = LongStream.range(1, 15).boxed().toList();
    basicIdList = LongStream.range(1, 20).boxed().toList();
    blogIdList = LongStream.range(1, 420).boxed().toList();
    postIdList = LongStream.range(1, 4200).boxed().toList();
    channelIdList = LongStream.range(1, 840).boxed().toList();
  }

}
