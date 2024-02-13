package run.freshr.domain.predefined.repository.elasticsearch;

import org.springframework.data.domain.Page;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;
import run.freshr.domain.statistic.elasticsearch.StatisticHashtag;

public interface StatisticHashtagRepositoryCustom {

  Page<StatisticHashtag> getPage(PredefinedStringSearch search);

}
