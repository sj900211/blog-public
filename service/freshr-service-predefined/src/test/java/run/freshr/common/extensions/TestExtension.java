package run.freshr.common.extensions;

import static run.freshr.TestRunner.managerMajorId;
import static run.freshr.TestRunner.managerMinorId;
import static run.freshr.TestRunner.userId;
import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;
import static run.freshr.domain.auth.enumerations.Role.ROLE_ANONYMOUS;
import static run.freshr.domain.auth.enumerations.Role.ROLE_MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.ROLE_MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.ROLE_USER;

import com.redis.testcontainers.RedisContainer;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import run.freshr.common.extension.TestExtensionAware;
import run.freshr.domain.auth.enumerations.Role;
import run.freshr.service.TestService;

@Slf4j
@Testcontainers
public abstract class TestExtension extends TestExtensionAware {

  private static final String KAFKA_IMAGE = "confluentinc/cp-kafka:latest";
  private static final String REDIS_IMAGE = "redis:latest";
  private static final String ELASTICSEARCH_IMAGE = "elasticsearch:8.7.0";
  private static final String POSTGRES_IMAGE = "postgres:latest";

  @ClassRule
  public static final KafkaContainer KAFKA_CONTAINER;
  @ClassRule
  public static final RedisContainer REDIS_CONTAINER;
  @ClassRule
  public static final ElasticsearchContainer ELASTICSEARCH_CONTAINER;
  @ClassRule
  public static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

  static {
    KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse(KAFKA_IMAGE));

    REDIS_CONTAINER = new RedisContainer(DockerImageName.parse(REDIS_IMAGE))
        .withExposedPorts(6379)
        .withCommand("redis-server --requirepass redis-password");

    ELASTICSEARCH_CONTAINER = new ElasticsearchContainer(
        DockerImageName
            .parse(ELASTICSEARCH_IMAGE)
            .asCompatibleSubstituteFor(
                "docker.elastic.co/elasticsearch/elasticsearch")
    ).withExposedPorts(9200, 9300)
        .withClasspathResourceMapping(
            "docker/env/elasticsearch/config/elasticsearch.yml",
            "/usr/share/elasticsearch/config/elasticsearch.yml",
            BindMode.READ_ONLY)
        .withEnv(new HashMap<>() {{
          put("node.name", "elasticsearch");
          put("ELASTIC_PASSWORD", "elasticsearch-password");
          put("discovery.type", "single-node");
        }});

    POSTGRES_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE))
        .withUsername("postgres")
        .withPassword("postgres")
        .withDatabaseName("postgres");
    POSTGRES_CONTAINER
        .withInitScript("docker/env/postgres/test/schema.sql");

    KAFKA_CONTAINER.start();
    REDIS_CONTAINER.start();
    ELASTICSEARCH_CONTAINER.start();
    POSTGRES_CONTAINER.start();
  }

  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");

    registry.add("spring.kafka.bootstrap-servers", () ->
        "localhost:" + KAFKA_CONTAINER.getMappedPort(9093));

    registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
    registry.add("spring.data.redis.port", () ->
        REDIS_CONTAINER.getMappedPort(6379));
    registry.add("spring.data.redis.password", () -> "redis-password");

    registry.add("spring.elasticsearch.uris", () -> {
      System.out.println("\"http://localhost:\" + ELASTICSEARCH_CONTAINER.getMappedPort(9200) = "
          + "http://localhost:" + ELASTICSEARCH_CONTAINER.getMappedPort(9200));
      return "http://localhost:" + ELASTICSEARCH_CONTAINER.getMappedPort(9200);
    });
    registry.add("spring.elasticsearch.password", () -> "elasticsearch-password");

    registry.add("spring.datasource.url", () ->
        "jdbc:postgresql://localhost:"
            + POSTGRES_CONTAINER.getMappedPort(5432)
            + "/postgres");
  }

  @Autowired
  protected TestService service;

  /**
   * 인증 정보 설정
   *
   * @param id   일련 번호
   * @param role 권한
   * @apiNote 인증 정보 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  private void authentication(Long id, Role role) {
    log.info("TestExtension.authentication");

    removeSigned(); // 로그아웃 처리

    if (!role.equals(ROLE_ANONYMOUS)) { // 게스트 권한이 아닐 경우
      service.createAuth(id, role); // 토큰 발급 및 등록
    }

    signedRole.set(role); // 로그인한 계정 권한 설정
    signedId.set(id); // 로그인한 계정 일련 번호 설정

    SecurityContextHolder // 일회용 로그인 설정
        .getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(
            role.getPrivilege(),
            "{noop}",
            AuthorityUtils.createAuthorityList(role.getKey())
        ));
  }

  /**
   * 인증 정보 생성
   *
   * @apiNote ROLE_MANAGER_MAJOR 권한으로 인증 정보 생성
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedManagerMajor() {
    log.info("TestExtension.setSignedManagerMajor");

    authentication(managerMajorId, ROLE_MANAGER_MAJOR);
  }

  /**
   * 인증 정보 생성
   *
   * @apiNote ROLE_MANAGER_MINOR 권한으로 인증 정보 생성
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedManagerMinor() {
    log.info("TestExtension.setSignedManagerMinor");

    authentication(managerMinorId, ROLE_MANAGER_MINOR);
  }

  /**
   * 인증 정보 생성
   *
   * @apiNote ROLE_USER 권한으로 인증 정보 생성
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setSignedUser() {
    log.info("TestExtension.setSignedUser");

    authentication(userId, ROLE_USER);
  }

  /**
   * 인증 정보 생성
   *
   * @apiNote ROLE_ANONYMOUS 권한으로 인증 정보 생성
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setAnonymous() {
    log.info("TestExtension.setAnonymous");

    authentication(0L, ROLE_ANONYMOUS);
  }

  /**
   * RSA 정보 생성
   *
   * @apiNote RSA 정보 생성
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void setRsa() {
    log.info("TestExtension.setRsa");

    service.createRsa();
  }

}
