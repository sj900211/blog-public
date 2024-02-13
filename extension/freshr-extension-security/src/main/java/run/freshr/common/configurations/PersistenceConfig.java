package run.freshr.common.configurations;

import run.freshr.domain.account.entity.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * JPA Auditing 설정
 *
 * @author 류성재
 * @apiNote JPA Auditing 설정
 * @since 2023. 6. 16. 오전 9:22:07
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@EnableElasticsearchAuditing(auditorAwareRef="elasticsearchAuditorProvider")
@EnableRedisRepositories("**.**.domain.**.repository.redis")
@EnableElasticsearchRepositories("**.**.domain.**.repository.elasticsearch")
@EnableJpaRepositories("**.**.domain.**.repository.jpa")
public class PersistenceConfig {

  @Bean
  public AuditorAware<Account> auditorProvider() {
    return new AuditorAwareImpl();
  }

  @Bean
  public AuditorAware<Void> elasticsearchAuditorProvider() {
    return new ElasticsearchAuditorAwareImpl();
  }

}
