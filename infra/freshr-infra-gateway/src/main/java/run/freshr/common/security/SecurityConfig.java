package run.freshr.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security 설정
 *
 * @author 류성재
 * @apiNote Security 설정
 * @since 2023. 6. 16. 오후 2:43:56
 */
@Configuration
public class SecurityConfig {

  /**
   * Security 설정
   *
   * @param security security
   * @return security web filter chain
   * @apiNote Security 설정
   * @author 류성재
   * @since 2023. 6. 16. 오후 2:43:56
   */
  @Bean
  public SecurityWebFilterChain filterChain(ServerHttpSecurity security) {
    security.csrf(CsrfSpec::disable);

    return security.build();
  }

}
