package run.freshr.common.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Get Parameter VO 필드에 적용할 Annotation
 *
 * @author 류성재
 * @apiNote Get Parameter VO 필드에 적용할 Annotation
 * @since 2023. 1. 12. 오후 2:45:22
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface SearchComment {

  /**
   * 설명
   *
   * @return the string
   * @apiNote 필드에 대한 설명
   * @author 류성재
   * @since 2023. 1. 12. 오후 2:45:22
   */
  String value() default "";

}
