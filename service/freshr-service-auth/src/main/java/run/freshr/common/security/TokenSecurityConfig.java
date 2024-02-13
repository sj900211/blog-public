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
 * @since 2023. 6. 16. 오후 3:38:53
 */
@RequiredArgsConstructor
public class TokenSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final TokenProvider tokenProvider;

  /**
   * 권한 Filter 에 TokenProvider 주입
   *
   * @param http {@link HttpSecurity}
   * @apiNote 권한 Filter 에 TokenProvider 주입
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:38:53
   */
  @Override
  public void configure(HttpSecurity http) {
    TokenAuthenticationFilter filter = new TokenAuthenticationFilter(tokenProvider);

    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
  }

}
