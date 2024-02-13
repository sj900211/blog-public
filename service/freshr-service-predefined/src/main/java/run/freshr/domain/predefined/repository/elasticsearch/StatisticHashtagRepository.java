package run.freshr.domain.predefined.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import run.freshr.domain.statistic.elasticsearch.StatisticHashtag;

public interface StatisticHashtagRepository extends
    ElasticsearchRepository<StatisticHashtag, String>, StatisticHashtagRepositoryCustom {

}
