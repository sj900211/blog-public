package run.freshr.common.security;

import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * Access 토큰 유효성 검증 Filter
 *
 * @author 류성재
 * @apiNote Access 토큰 유효성 검증 Filter
 * @since 2023. 6. 16. 오후 2:39:14
 */
@Slf4j
@Component
public class AccessTokenAuthenticationFilter extends
    AbstractGatewayFilterFactory<AccessTokenAuthenticationFilter.Config> {

  private final TokenProvider provider;

  public AccessTokenAuthenticationFilter(TokenProvider provider) {
    super(Config.class);
    this.provider = provider;
  }

  public static class Config {
  }

  /**
   * 토큰 유효성 검증
   *
   * @param config config
   * @return gateway filter
   * @apiNote 토큰 유효성 검증
   * @author 류성재
   * @since 2023. 6. 16. 오후 2:39:14
   */
  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      String token = provider.resolve(exchange.getRequest());
      boolean flag = provider.validateAccess(token);

      if (!flag) {
        return provider.fail(exchange, "Not found token data", UNAUTHORIZED);
      }

      flag = provider.validateExpired(token);

      if (!flag) {
        return provider.fail(exchange, "Expired token", GONE);
      }

      return chain.filter(exchange);
    };
  }

}
