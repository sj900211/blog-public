package run.freshr.domain.predefined.unit.jpa;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.common.data.CursorData;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.repository.jpa.HashtagRepository;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagUnitImpl implements HashtagUnit {

  private final HashtagRepository repository;

  @Override
  @Transactional
  public String create(Hashtag entity) {
    log.info("HashtagUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  @Transactional
  public List<String> create(List<Hashtag> entities) {
    log.info("HashtagUnit.create");

    return repository
        .saveAll(entities)
        .stream()
        .map(Hashtag::getId)
        .collect(Collectors.toList());
  }

  @Override
  public Boolean exists(String id) {
    log.info("HashtagUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Hashtag get(String id) {
    log.info("HashtagUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public List<Hashtag> search(PredefinedStringSearch search) {
    log.info("HashtagUnit.search");

    return repository.search(search);
  }

  @Override
  public CursorData<Hashtag> getPage(PredefinedStringSearch search) {
    log.info("HashtagUnit.getPage");

    return repository.getPage(search);
  }

}
