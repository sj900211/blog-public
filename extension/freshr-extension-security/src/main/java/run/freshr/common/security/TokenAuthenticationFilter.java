package run.freshr.common.security;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 토큰 인가 Filter
 *
 * @author 류성재
 * @apiNote 토큰 인가 Filter
 * @since 2023. 6. 16. 오전 9:43:00
 */
@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private TokenProvider provider;

  public TokenAuthenticationFilter(TokenProvider provider) {
    this.provider = provider;
  }

  /**
   * filter 프로세스
   *
   * @param request     request
   * @param response    response
   * @param filterChain filter chain
   * @apiNote {@link TokenProvider} 를 사용해서 인가 처리
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:43:00
   */
  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) {
    log.debug("**** SECURITY FILTER START");

    try {
      String accessToken = provider.resolve(request);

      provider.setThreadLocal(accessToken);

      SecurityContextHolder.getContext().setAuthentication(provider.getAuthentication());

      log.debug("**** Role: " + signedRole.get().name());
      log.debug("**** Id: " + signedId.get());

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      log.error("**** Exception ****");
      log.error("**** error message : " + e.getMessage());
      log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

      SecurityContextHolder.clearContext();

      response.setStatus(INTERNAL_SERVER_ERROR.value());

      log.error(e.getMessage(), e);
    } finally {
      log.debug("**** SECURITY FILTER FINISH");
    }
  }

}
