package run.freshr.common.security;

import static run.freshr.common.utils.CryptoUtil.encodeBase64;
import static run.freshr.common.utils.CryptoUtil.encryptSha256;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StringUtils.hasLength;

import run.freshr.domain.auth.unit.redis.AccessRedisUnit;
import run.freshr.domain.auth.unit.redis.RefreshRedisUnit;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.List;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Token 관리 기능 정의
 *
 * @author 류성재
 * @apiNote Token 관리 기능 정의<br>
 *          토큰에 대한 유효성 검증을 진행하기 위해 필요한 기능만 정의
 * @since 2023. 6. 16. 오후 3:15:18
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

  private final AccessRedisUnit accessRedisUnit;
  private final RefreshRedisUnit refreshRedisUnit;

  public static final String JWT_VARIABLE = "FRESH-R"; // JWT 암호화 키 값 TODO: 설정 파일에서 관리할 수 있도록 수정 예정
  public static final String JWT_SHA = encryptSha256(JWT_VARIABLE);
  public static final byte[] JWT_BYTE = encodeBase64(JWT_SHA).getBytes(UTF_8); // JWT 암호화 키 Byte
  public static final Key JWT_KEY = new SecretKeySpec(JWT_BYTE, HS512.getJcaName()); // Key 생성
  public static final String AUTHORIZATION_STRING = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";

  /**
   * 토큰 조회
   *
   * @param request 요청 정보
   * @return string
   * @apiNote 토큰 조회<br>
   *          요청 정보에서 토큰 값만 분리
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:15:18
   */
  public String resolve(ServerHttpRequest request) {
    List<String> authorizationList = request.getHeaders().get(AUTHORIZATION_STRING);

    if (Objects.isNull(authorizationList)) {
      return null;
    }

    if (authorizationList.isEmpty()) {
      return null;
    }

    String header = authorizationList.get(0);

    if (!header.contains(BEARER_PREFIX)) {
      return null;
    }

    return hasLength(header) ? header.replace(BEARER_PREFIX, "") : null;
  }

  /**
   * claim 조회
   *
   * @param jwt JWT 토큰
   * @return claims
   * @throws JwtException jwt exception
   * @apiNote 토큰에서 claim 정보 조회
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:15:18
   */
  public Claims get(final String jwt) throws JwtException {
    return Jwts
        .parserBuilder()
        .setSigningKey(JWT_KEY)
        .build()
        .parseClaimsJws(jwt)
        .getBody();
  }

  /**
   * Access 토큰이 Redis 에 있는지 체크
   *
   * @param token Access 토큰
   * @return boolean
   * @apiNote Access 토큰이 Redis 에 있는지 체크
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:15:18
   */
  public boolean validateAccess(final String token) {
    if (!hasLength(token)) {
      return true;
    }

    return accessRedisUnit.exists(token);
  }

  /**
   * Refresh 토큰이 Redis 에 있는지 체크
   *
   * @param token Access 토큰
   * @return boolean
   * @apiNote Refresh 토큰이 Redis 에 있는지 체크
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:15:18
   */
  public boolean validateRefresh(final String token) {
    if (!hasLength(token)) {
      return true;
    }

    return refreshRedisUnit.exists(token);
  }

  /**
   * 토큰이 만료되었는지 체크
   *
   * @param token JWT 토큰
   * @return boolean
   * @apiNote 토큰이 만료되었는지 체크
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:15:18
   */
  public boolean validateExpired(final String token) {
    if (!hasLength(token)) {
      return true;
    }

    return !checkExpiration(token);
  }

  /**
   * 토큰이 만료되었는지 체크
   *
   * @param jwt JWT 토큰
   * @return boolean
   * @throws JwtException jwt exception
   * @apiNote 토큰이 만료되었는지 체크
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:15:18
   */
  public boolean checkExpiration(final String jwt) throws JwtException {
    boolean flag = false;

    try {
      get(jwt);
    } catch (ExpiredJwtException e) {
      flag = true;
    }

    return flag;
  }

  /**
   * 실패 처리
   *
   * @param exchange exchange
   * @param message  실패 메시지
   * @param status   HTTP Status 코드
   * @return mono
   * @apiNote 실패 처리
   * @author 류성재
   * @since 2023. 6. 16. 오후 2:41:36
   */
  public Mono<Void> fail(ServerWebExchange exchange, String message, HttpStatus status) {
    ServerHttpResponse response = exchange.getResponse();

    response.setStatusCode(status);

    return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes(UTF_8))));
  }

}
