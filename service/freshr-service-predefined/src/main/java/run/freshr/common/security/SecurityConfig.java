package run.freshr.common.security;

import static run.freshr.common.configurations.URIConfig.uriCommonHeartbeat;
import static run.freshr.common.configurations.URIConfig.uriDocsAll;
import static run.freshr.common.configurations.URIConfig.uriFavicon;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue;

/**
 * Security 설정
 *
 * @author 류성재
 * @apiNote Security 설정
 * @since 2023. 6. 16. 오후 3:35:57
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  private final TokenProvider tokenProvider;

  /**
   * 비밀번호 암호화 방식 설정
   *
   * @return password encoder
   * @apiNote 비밀번호 암호화 방식 설정
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:35:57
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 권한 체크에서 제외할 URI 설정
   *
   * @return web security customizer
   * @apiNote 권한 체크에서 제외할 URI 설정
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:35:57
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(uriDocsAll, uriFavicon)
        .requestMatchers(GET, uriCommonHeartbeat);
  }

  /**
   * Security 설정
   *
   * @param httpSecurity http security
   * @return security filter chain
   * @throws Exception exception
   * @apiNote Security 설정
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:35:57
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(configurer -> configurer.sessionCreationPolicy(STATELESS))
        .authorizeHttpRequests(registry -> registry.anyRequest().permitAll())
        .headers(headers ->
            headers
                .xssProtection(xss -> xss.headerValue(HeaderValue.ENABLED_MODE_BLOCK))
                .contentSecurityPolicy(policy -> policy.policyDirectives("script-src 'self'")))
        .apply(new TokenSecurityConfig(tokenProvider));

    return httpSecurity.build();
  }

}
