package run.freshr.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * 유닛 서비스
 *
 * @author 류성재
 * @apiNote 논리적인 Architecture 를 설계하기 위한 Annotation<br>
 *          Service 와 똑같은 기능
 * @since 2023. 6. 26. 오후 4:12:15
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Unit {

  /**
   * Component class
   *
   * @return string
   * @apiNote Service 에 있는 value 와 똑같은 기능
   * @author 류성재
   * @since 2023. 6. 26. 오후 4:12:16
   */
  @AliasFor(annotation = Component.class)
  String value() default "";

}
