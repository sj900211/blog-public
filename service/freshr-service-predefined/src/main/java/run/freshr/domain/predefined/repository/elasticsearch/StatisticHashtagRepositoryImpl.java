package run.freshr.domain.predefined.repository.elasticsearch;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.utils.RestUtilAware.field;
import static run.freshr.domain.predefined.entity.QHashtag.hashtag;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;
import run.freshr.domain.statistic.elasticsearch.StatisticHashtag;

@RequiredArgsConstructor
public class StatisticHashtagRepositoryImpl implements StatisticHashtagRepositoryCustom {

  private final ElasticsearchOperations elasticsearchOperations;

  @Override
  public Page<StatisticHashtag> getPage(PredefinedStringSearch search) {
    PageRequest pageRequest = PageRequest.of(
        search.getPage() - 1,
        search.getSize(),
        Sort.by(field(hashtag.id)).ascending());

    NativeQueryBuilder queryBuilder = new NativeQueryBuilder()
        .withPageable(pageRequest);

    String word = search.getWord();

    if (hasLength(word)) {
      queryBuilder.withFilter(filter ->
          filter.regexp(regex ->
              regex.field(field(hashtag.id)).value(".*" + word + ".*")));
    }

    SearchHits<StatisticHashtag> searchHits = elasticsearchOperations
        .search(queryBuilder.build(), StatisticHashtag.class);
    long totalHits = searchHits.getTotalHits();

    List<StatisticHashtag> list = searchHits
        .stream()
        .map(SearchHit::getContent)
        .toList();

    return new PageImpl<>(list, pageRequest, totalHits);
  }

}
