package run.freshr.common.logging;

import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;
import static run.freshr.domain.auth.enumerations.Role.ROLE_ANONYMOUS;
import static java.util.Optional.ofNullable;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 전역 로깅 설정
 *
 * @author 류성재
 * @apiNote 전역 로깅 설정
 * @since 2023. 6. 16. 오전 9:25:49
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

  /**
   * Controller 로깅 설정
   *
   * @param proceedingJoinPoint AOP 대상에 대한 정보
   * @return object
   * @throws Throwable throwable
   * @apiNote Controller 로깅 설정
   * @author 류성재
   * @since 2023. 6. 16. 오전 9:25:50
   */
  @Around("execution(* *.*.controller..*.*(..))")
  public Object controllerLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    log.info("**** LOG START");
    log.info(
        "**** " + proceedingJoinPoint.getSignature().getDeclaringTypeName()
            + "." + proceedingJoinPoint.getSignature().getName()
    );
    log.info("**** Role: " + ofNullable(signedRole.get()).orElse(ROLE_ANONYMOUS).getKey());
    log.info("**** Id: " + ofNullable(signedId.get()).orElse(0L));

    Object result = proceedingJoinPoint.proceed();

    log.info("**** LOG FINISH");

    return result;
  }

}
