package run.freshr.common.configurations;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QueryDsl 설정
 *
 * @author 류성재
 * @apiNote JPAQuery 를 프로젝트 전역에서 주입받을 수 있도록 설정
 * @since 2023. 6. 15. 오후 1:34:01
 */
@Getter
@Configuration
public class QueryDslConfig {

  @PersistenceContext
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

}
