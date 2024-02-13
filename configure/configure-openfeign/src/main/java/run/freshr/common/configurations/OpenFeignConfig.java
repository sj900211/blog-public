package run.freshr.common.configurations;

import feign.Retryer;
import feign.Retryer.Default;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

@Configuration
@EnableFeignClients("**.**.openfeign.**")
public class OpenFeignConfig {

  @Bean
  public Retryer.Default retryer() {
    return new Default(100, TimeUnit.SECONDS.toMillis(3), 5);
  }

  @Bean
  public FeignFormatterRegistrar dateTimeFormatterRegistrar() {
    return registry -> {
      DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();

      registrar.setUseIsoFormat(true);
      registrar.registerFormatters(registry);
    };
  }

}
