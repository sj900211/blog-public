package run.freshr.controller;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import org.springframework.aot.hint.BindingReflectionHintsRegistrar;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.ReflectionHints;
import org.springframework.aot.hint.annotation.ReflectiveProcessor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

public class TestControllerMappingReflectiveProcessor implements ReflectiveProcessor {

  private final BindingReflectionHintsRegistrar bindingRegistrar = new BindingReflectionHintsRegistrar();


  @Override
  public void registerReflectionHints(ReflectionHints hints, AnnotatedElement element) {
    if (element instanceof Class<?> type) {
      registerTypeHints(hints, type);
    }
    else if (element instanceof Method method) {
      registerMethodHints(hints, method);
    }
  }

  protected final BindingReflectionHintsRegistrar getBindingRegistrar() {
    return this.bindingRegistrar;
  }

  protected void registerTypeHints(ReflectionHints hints, Class<?> type) {
    hints.registerType(type);
  }

  protected void registerMethodHints(ReflectionHints hints, Method method) {
    hints.registerMethod(method, ExecutableMode.INVOKE);
    for (Parameter parameter : method.getParameters()) {
      registerParameterTypeHints(hints, MethodParameter.forParameter(parameter));
    }
    registerReturnTypeHints(hints, MethodParameter.forExecutable(method, -1));
  }

  protected void registerParameterTypeHints(ReflectionHints hints, MethodParameter methodParameter) {
    if (methodParameter.hasParameterAnnotation(RequestBody.class) ||
        methodParameter.hasParameterAnnotation(ModelAttribute.class) ||
        methodParameter.hasParameterAnnotation(RequestPart.class)) {
      this.bindingRegistrar.registerReflectionHints(hints, methodParameter.getGenericParameterType());
    }
    else if (HttpEntity.class.isAssignableFrom(methodParameter.getParameterType())) {
      this.bindingRegistrar.registerReflectionHints(hints, getHttpEntityType(methodParameter));
    }
  }

  protected void registerReturnTypeHints(ReflectionHints hints, MethodParameter returnTypeParameter) {
    if (AnnotatedElementUtils.hasAnnotation(returnTypeParameter.getContainingClass(), ResponseBody.class) ||
        returnTypeParameter.hasMethodAnnotation(ResponseBody.class)) {
      this.bindingRegistrar.registerReflectionHints(hints, returnTypeParameter.getGenericParameterType());
    }
    else if (HttpEntity.class.isAssignableFrom(returnTypeParameter.getParameterType())) {
      this.bindingRegistrar.registerReflectionHints(hints, getHttpEntityType(returnTypeParameter));
    }
  }

  @Nullable
  private Type getHttpEntityType(MethodParameter parameter) {
    MethodParameter nestedParameter = parameter.nested();
    return (nestedParameter.getNestedParameterType() == nestedParameter.getParameterType() ?
        null : nestedParameter.getNestedParameterType());
  }

}
