package run.freshr.common.security;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
import static run.freshr.common.enumerations.StatusEnum.EXPIRED_JWT;
import static run.freshr.common.utils.CryptoUtil.encodeBase64;
import static run.freshr.common.utils.CryptoUtil.encryptSha256;
import static run.freshr.domain.auth.enumerations.Role.ROLE_ANONYMOUS;
import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.hasLength;

import run.freshr.common.data.ResponseData;
import run.freshr.domain.auth.enumerations.Role;
import run.freshr.domain.auth.redis.AccessRedis;
import run.freshr.domain.auth.unit.redis.AccessRedisUnit;
import run.freshr.domain.auth.unit.redis.RefreshRedisUnit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Token 관리 기능 정의
 *
 * @author 류성재
 * @apiNote Token 관리 기능 정의
 * @since 2023. 6. 16. 오전 9:45:23
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

  private final AccessRedisUnit accessRedisUnit;
  private final RefreshRedisUnit refreshRedisUnit;

  private final ObjectMapper objectMapper;

  public static final String JWT_VARIABLE = "FRESH-R"; // JWT 암호화 키 값 TODO: 설정 파일에서 관리할 수 있도록
  // 수정 예정
  public static final String JWT_SHA = encryptSha256(JWT_VARIABLE);
  public static final byte[] JWT_BYTE = encodeBase64(JWT_SHA).getBytes(UTF_8); // JWT 암호화 키 Byte
  public static final Key JWT_KEY = new SecretKeySpec(JWT_BYTE, HS512.getJcaName()); // Key 생성
  public static final Integer EXPIRATION_ACCESS = 1000 * 60 * 15; // Access Token 만료 시간 TODO: 설정 파일에서 관리할 수 있도록 수정 예정
  public static final String AUTHORIZATION_STRING = "Authorization";
  public static final String BEARER_PREFIX = "Bearer ";
  public static ThreadLocal<Long> signedId = new ThreadLocal<>(); // 요청한 토큰의 계정 일련 번호
  public static ThreadLocal<Role> signedRole = new ThreadLocal<>(); // 요청한 토큰의 계정 권한

  /**
   * Access 토큰 발급
   *
   * @param id 일련 번호
   * @return string
   * @apiNote Access 토큰 발급
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:40:56
   */
  public String generateAccessToken(final Long id) {
    return generate(id.toString(), EXPIRATION_ACCESS, null);
  }

  /**
   * Refresh 토큰 발급
   *
   * @param id 일련 번호
   * @return string
   * @apiNote Refresh 토큰 발급
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:42:54
   */
  public String generateRefreshToken(final Long id) {
    return generate(id.toString(), null, null);
  }

  /**
   * 토큰 발급
   *
   * @param subject    토큰 제목
   * @param expiration 만료 기간
   * @param claim      토큰 claim
   * @return string
   * @apiNote 토큰 발급
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:43:33
   */
  public String generate(final String subject, final Integer expiration,
      final HashMap<String, Object> claim) {
    JwtBuilder jwtBuilder = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject(subject)
        .setIssuedAt(new Date())
        .signWith(JWT_KEY);

    if (!isNull(claim)) { // 토큰 Body 설정
      jwtBuilder.setClaims(claim);
    }

    if (!isNull(expiration)) { // 만료 시간 설정
      jwtBuilder.setExpiration(new Date(new Date().getTime() + expiration));
    }

    return jwtBuilder.compact();
  }

  /**
   * 요청 정보에서 토큰 조회
   *
   * @param request 요청 정보
   * @return string
   * @apiNote 요청 정보에서 토큰 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:45:23
   */
  public String resolve(HttpServletRequest request) {
    String header = request.getHeader(AUTHORIZATION_STRING);

    return hasLength(header) ? header.replace(BEARER_PREFIX, "") : null;
  }
  /**
   * 요청 정보에서 토큰 조회
   *
   * @param request 요청 정보
   * @return string
   * @apiNote 요청 정보에서 토큰 조회
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

    return hasLength(header) ? header.replace(BEARER_PREFIX, "") : null;
  }

  /**
   * JWT 토큰 정보를 조회
   *
   * @param jwt jwt 토큰
   * @return claims
   * @throws JwtException jwt exception
   * @apiNote JWT 토큰 정보를 조회
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:45:23
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

    if (!accessRedisUnit.exists(token)) { // 발급한 토큰인지 체크
      throw new ExpiredJwtException(null, null, "error validate token");
    }

    if (checkExpiration(token)) { // 만료되었는지 체크
      throw new ExpiredJwtException(null, null, "error validate token");
    }

    return true;
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

    if (!refreshRedisUnit.exists(token)) { // 발급한 토큰인지 체크
      throw new ExpiredJwtException(null, null, "error validate token");
    }

    if (checkExpiration(token)) { // 만료되었는지 체크
      throw new ExpiredJwtException(null, null, "error validate token");
    }

    return true;
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
   * 토큰 정보 저장
   *
   * @param accessToken Access 토큰
   * @apiNote 통신이 유지되는 동안 프로젝트 전역에서 접근할 수 있도록 토큰 정보를 저장
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:45:23
   */
  public void setThreadLocal(String accessToken) {
    Long id = 0L;
    Role role = ROLE_ANONYMOUS;

    if (hasLength(accessToken)) {
      AccessRedis access = accessRedisUnit.get(accessToken);

      id = access.getSignId();
      role = access.getRole();
    }

    signedId.set(id);
    signedRole.set(role);
  }

  /**
   * 인증 정보 생성
   *
   * @return authentication
   * @apiNote 인증 정보 생성
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:45:23
   */
  public Authentication getAuthentication() {
    Role role = signedRole.get();

    return new UsernamePasswordAuthenticationToken(
        role.getPrivilege(),
        "{noop}",
        createAuthorityList(role.getKey())
    );
  }

  /**
   * 토큰 만료 내용 작성
   *
   * @return string
   * @throws JsonProcessingException json processing exception
   * @apiNote 토큰 만료 에러인 경우 반환 데이터에 담길 내용을 작성
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:52:52
   */
  public String expiredJwt() throws JsonProcessingException {
    return objectMapper.writeValueAsString(ResponseData
        .builder()
        .name(UPPER_CAMEL.to(LOWER_HYPHEN, EXPIRED_JWT.name()))
        .message(EXPIRED_JWT.getMessage())
        .build());
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
