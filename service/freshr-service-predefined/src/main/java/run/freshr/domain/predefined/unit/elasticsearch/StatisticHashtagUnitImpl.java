package run.freshr.domain.predefined.unit.elasticsearch;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.predefined.repository.elasticsearch.StatisticHashtagRepository;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;
import run.freshr.domain.statistic.elasticsearch.StatisticHashtag;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticHashtagUnitImpl implements StatisticHashtagUnit {

  private final StatisticHashtagRepository repository;

  @Override
  @Transactional
  public void save(StatisticHashtag document) {
    log.info("StatisticHashtagUnit.save");

    repository.save(document);
  }

  @Override
  public Boolean exists(String id) {
    log.info("StatisticHashtagUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public StatisticHashtag get(String id) {
    log.info("StatisticHashtagUnit.get");

    return repository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("StatisticHashtagUnit.delete");

    repository.deleteById(id);
  }

  @Override
  public Page<StatisticHashtag> getPage(PredefinedStringSearch search) {
    log.info("StatisticHashtagUnit.getPage");

    return repository.getPage(search);
  }

}
