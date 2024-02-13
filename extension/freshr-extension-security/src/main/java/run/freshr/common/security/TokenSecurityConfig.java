package run.freshr.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Token security config.
 *
 * @author 류성재
 * @apiNote api note
 * @since 2023. 6. 16. 오전 9:49:29
 */
@RequiredArgsConstructor
public class TokenSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final TokenProvider tokenProvider;

  /**
   * 토큰 인가 Filter 에 TokenProvider 주입
   *
   * @param http Security 설정
   * @apiNote 토큰 인가 Filter 에 {@link TokenProvider} 주입
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:49:29
   */
  @Override
  public void configure(HttpSecurity http) {
    TokenAuthenticationFilter filter = new TokenAuthenticationFilter(tokenProvider);

    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }

}
