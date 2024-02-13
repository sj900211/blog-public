package run.freshr.common.configurations;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 추가 설정
 *
 * @author 류성재
 * @apiNote 추가 설정
 * @since 2023. 6. 16. 오후 3:34:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Configuration
@EnableConfigurationProperties
public class CustomConfig extends CustomConfigAware {

}
