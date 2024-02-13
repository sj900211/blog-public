package run.freshr.controller;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TestRequestMapping(method = TestMethod.TOGGLE)
public @interface ToggleMapping {

  @AliasFor(annotation = TestRequestMapping.class)
  String name() default "";

  @AliasFor(annotation = TestRequestMapping.class)
  String[] value() default {};

  @AliasFor(annotation = TestRequestMapping.class)
  String[] path() default {};

  @AliasFor(annotation = TestRequestMapping.class)
  String[] params() default {};

  @AliasFor(annotation = TestRequestMapping.class)
  String[] headers() default {};

  @AliasFor(annotation = TestRequestMapping.class)
  String[] consumes() default {};

  @AliasFor(annotation = TestRequestMapping.class)
  String[] produces() default {};

}
