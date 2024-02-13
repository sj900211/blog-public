package run.freshr.domain.predefined.unit.elasticsearch;

import org.springframework.data.domain.Page;
import run.freshr.common.extensions.unit.UnitDocumentDefaultExtension;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;
import run.freshr.domain.statistic.elasticsearch.StatisticHashtag;

public interface StatisticHashtagUnit extends
    UnitDocumentDefaultExtension<StatisticHashtag, String> {

  Page<StatisticHashtag> getPage(PredefinedStringSearch search);

}
