package run.freshr.common.security;

import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 인증인가 Filter
 *
 * @author 류성재
 * @apiNote 인증인가 Filter
 * @since 2023. 6. 16. 오후 3:37:20
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
   * @apiNote filter 프로세스
   * @author 류성재
   * @since 2023. 6. 16. 오후 3:37:20
   */
  @Override
  protected void doFilterInternal(@NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) {
    log.debug("**** SECURITY FILTER START");

    try {
      String accessToken = provider.resolve(request);

      provider.validateAccess(accessToken);
      provider.setThreadLocal(accessToken);

      SecurityContextHolder.getContext().setAuthentication(provider.getAuthentication());

      log.debug("**** Role: " + signedRole.get().name());
      log.debug("**** Id: " + signedId.get());

      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      log.error("**** ExpiredJwtException ****");
      log.error("**** error message : " + e.getMessage());
      log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

      SecurityContextHolder.clearContext();

      try {
        response.getWriter().write(provider.expiredJwt());
      } catch (IOException ex) {
        log.error("**** ExpiredJwtException > IOException ****");
        log.error("**** error message : " + e.getMessage());
        log.error("**** stack trace : " + Arrays.toString(e.getStackTrace()));

        log.error(e.getMessage(), e);
      }

      log.error(e.getMessage(), e);
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
