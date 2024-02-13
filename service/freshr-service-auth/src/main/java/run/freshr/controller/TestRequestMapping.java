package run.freshr.controller;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.Mapping;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
@Reflective(TestControllerMappingReflectiveProcessor.class)
public @interface TestRequestMapping {

  String name() default "";

  @AliasFor("path")
  String[] value() default {};

  @AliasFor("value")
  String[] path() default {};

  TestMethod[] method() default {};

  String[] params() default {};

  String[] headers() default {};

  String[] consumes() default {};

  String[] produces() default {};

}
