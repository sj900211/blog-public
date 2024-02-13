package run.freshr.domain.predefined.unit.jpa;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.predefined.entity.BasicImage;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.predefined.repository.jpa.BasicImageRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicImageUnitImpl implements BasicImageUnit {

  private final BasicImageRepository repository;

  @Override
  @Transactional
  public Long create(BasicImage entity) {
    log.info("BasicImageUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  @Transactional
  public List<Long> create(List<BasicImage> entities) {
    log.info("BasicImageUnit.create");

    return repository
        .saveAll(entities)
        .stream()
        .map(BasicImage::getId)
        .collect(Collectors.toList());
  }

  @Override
  public Boolean exists(Long id) {
    log.info("BasicImageUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public BasicImage get(Long id) {
    log.info("BasicImageUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public List<BasicImage> getList(BasicImageType type) {
    log.info("BasicImageUnit.getList");

    return repository.findAllByTypeOrderBySortAscIdDesc(type);
  }

  @Override
  public void deleteAll(BasicImageType type) {
    log.info("BasicImageUnit.deleteAll");

    repository.deleteAllByType(type);
  }

}
