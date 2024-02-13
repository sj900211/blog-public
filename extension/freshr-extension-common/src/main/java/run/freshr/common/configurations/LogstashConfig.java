package run.freshr.common.configurations;

import static ch.qos.logback.classic.Level.ALL;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import net.logstash.logback.appender.LogstashTcpSocketAppender;
import net.logstash.logback.encoder.LogstashEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Logstash 설정
 *
 * @author 류성재
 * @apiNote Logstash 설정
 * @since 2023. 6. 16. 오후 1:22:10
 */
@Profile({"!test & !local"})
@Configuration
public class LogstashConfig {

  private static String activeProfile; // 로그가 발생한 서비스의 실행 profile
  private static String logstashUrl; // logstash URL
  private static Integer logstashPort; // logstash Port
  private static String logName; // 로그가 발생한 서비스의 이름

  private static ObjectMapper objectMapper;

  @Autowired
  public LogstashConfig(ObjectMapper objectMapper) {
    LogstashConfig.objectMapper = objectMapper;
  }

  @Bean
  public static LoggerContext loggerContext() {
    return (LoggerContext) LoggerFactory.getILoggerFactory();
  }

  /**
   * 로그 내용 설정
   *
   * @param context context
   * @return logstash tcp socket appender
   * @throws JsonProcessingException json processing exception
   * @apiNote 로그 내용 설정<br>
   *          profile 과 name 을 추가로 전달하는 내용 추가
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:22:10
   */
  @Bean(initMethod="start", destroyMethod="stop")
  public static LogstashTcpSocketAppender logstashTcpSocketAppender(LoggerContext context)
      throws JsonProcessingException {
    LogstashTcpSocketAppender logstashTcpSocketAppender = new LogstashTcpSocketAppender();
    LogstashEncoder logstashEncoder = new LogstashEncoder();
    HashMap<String, String> map = new HashMap<>();

    map.put("name", logName);
    map.put("profile", activeProfile);

    logstashEncoder.setCustomFields(objectMapper.writeValueAsString(map));

    logstashTcpSocketAppender.addDestination(logstashUrl + ":" + logstashPort);
    logstashTcpSocketAppender.setEncoder(logstashEncoder);
    logstashTcpSocketAppender.setContext(context);

    return logstashTcpSocketAppender;
  }

  /**
   * 로그 범위 설정
   *
   * @param loggerContext             logger context
   * @param logstashTcpSocketAppender logstash tcp socket appender
   * @return logger
   * @apiNote 로그 범위를 설정
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:22:10
   */
  @Bean
  public static Logger registerSpringLogger(LoggerContext loggerContext,
      LogstashTcpSocketAppender logstashTcpSocketAppender) {
    Logger logger = loggerContext.getLogger("org.springframework");

    logger.setLevel(ALL);
    logger.addAppender(logstashTcpSocketAppender);

    return logger;
  }

  @Value("${spring.profiles.active}")
  public void setActiveProfile(String activeProfile) {
    LogstashConfig.activeProfile = activeProfile;
  }

  @Value("${freshr.logstash.url}")
  public void setLogstashUrl(String logstashUrl) {
    LogstashConfig.logstashUrl = logstashUrl;
  }

  @Value("${freshr.logstash.port}")
  public void setLogstashPort(Integer logstashPort) {
    LogstashConfig.logstashPort = logstashPort;
  }

  @Value("${freshr.logstash.name}")
  public void setLogName(String logName) {
    LogstashConfig.logName = logName;
  }

}
