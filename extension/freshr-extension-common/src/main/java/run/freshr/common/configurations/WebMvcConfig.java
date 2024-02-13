package run.freshr.common.configurations;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMVC 설정
 *
 * @author 류성재
 * @apiNote WebMVC 설정<br>
 *          Cross-Origin Resource Sharing 설정
 * @since 2023. 6. 16. 오후 1:32:47
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * 설정한 요청에 대해 CORS 허용
   *
   * @param registry registry
   * @apiNote 설정한 요청에 대해 CORS 허용<br>
   *          현재 모든 요청에 대해 허용하도록 설정되어 있음
   * @author 류성재
   * @since 2023. 6. 16. 오후 1:32:47
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods(GET.name(), POST.name(), PUT.name(), DELETE.name(), OPTIONS.name());
  }

}
