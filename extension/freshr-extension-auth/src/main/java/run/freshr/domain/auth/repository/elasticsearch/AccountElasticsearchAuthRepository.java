package run.freshr.domain.auth.repository.elasticsearch;

import run.freshr.domain.account.entity.Account;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AccountElasticsearchAuthRepository extends ElasticsearchRepository<Account, Long> {

}
