package run.freshr.domain.auth.unit.redis;

import static java.util.Objects.isNull;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.auth.redis.ChatAccessRedis;
import run.freshr.domain.auth.repository.redis.ChatAccessRedisRepository;
import run.freshr.rocketchat.client.RocketChatUserClient;
import run.freshr.rocketchat.configurations.RocketChatConfig;
import run.freshr.rocketchat.data.RocketChatSignData;
import run.freshr.rocketchat.dto.request.RocketChatLoginUserRequest;
import run.freshr.rocketchat.dto.response.RocketChatLoginUserResponse;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatAccessRedisUnitImpl implements ChatAccessRedisUnit {

  private final ChatAccessRedisRepository repository;

  private final RocketChatConfig rocketChatConfig;
  private final RocketChatUserClient rocketChatUserClient;

  @Override
  @Transactional
  public void save(ChatAccessRedis redis) {
    log.info("ChatAccessRedisUnit.save");

    repository.save(redis);
  }

  @Override
  public Boolean exists(String id) {
    log.info("ChatAccessRedisUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Boolean existsByUsername(String username) {
    log.info("ChatAccessRedisUnit.existsByUsername");

    Iterable<ChatAccessRedis> all = repository.findAll();
    Optional<ChatAccessRedis> data = StreamSupport.stream(all.spliterator(), false)
        .filter(redis -> redis.getUsername().equals(username))
        .findFirst();

    return data.isPresent();
  }

  @Override
  public ChatAccessRedis get(String id) {
    log.info("ChatAccessRedisUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public ChatAccessRedis getByUsername(String username) {
    log.info("ChatAccessRedisUnit.getByUsername");

    Iterable<ChatAccessRedis> all = repository.findAll();
    Optional<ChatAccessRedis> data = StreamSupport.stream(all.spliterator(), false)
        .filter(redis -> redis.getUsername().equals(username))
        .findFirst();

    return data.orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("ChatAccessRedisUnit.delete");

    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteByUsername(String username) {
    log.info("ChatAccessRedisUnit.deleteByUsername");

    Iterable<ChatAccessRedis> all = repository.findAll();
    Optional<ChatAccessRedis> data = StreamSupport.stream(all.spliterator(), false)
        .filter(redis -> redis.getUsername().equals(username))
        .findFirst();

    data.ifPresent(repository::delete);
  }

  @Override
  @Transactional
  public ChatAccessRedis getManager() throws HttpException {
    log.info("ChatAccessRedisUnit.getManager");

    String rocketChatUsername = rocketChatConfig.getManagerUsername();
    Boolean exists = existsByUsername(rocketChatUsername);

    if (exists) {
      return getByUsername(rocketChatUsername);
    } else {
      ResponseEntity<RocketChatLoginUserResponse> loginResponse = rocketChatUserClient.login(
          RocketChatLoginUserRequest
              .builder()
              .username(rocketChatUsername)
              .password(rocketChatConfig.getManagerPassword())
              .build());
      HttpStatusCode loginStatusCode = loginResponse.getStatusCode();

      if (loginStatusCode.isError()) {
        throw new HttpException("HTTP communication errors");
      }

      RocketChatLoginUserResponse loginBody = loginResponse.getBody();

      if (isNull(loginBody)) {
        throw new HttpException("Response is null");
      }

      String loginDataStatus = loginBody.getStatus();

      if (loginDataStatus.equalsIgnoreCase("SUCCESS")) {
        throw new HttpException("Login failed");
      }

      RocketChatSignData loginData = loginBody.getData();

      if (isNull(loginData)) {
        throw new HttpException("Login response is null");
      }

      String authToken = loginData.getAuthToken();
      String userId = loginData.getUserId();

      ChatAccessRedis redis = ChatAccessRedis
          .builder()
          .id(userId)
          .token(authToken)
          .username(rocketChatUsername)
          .build();

      save(redis);

      return redis;
    }
  }

}
