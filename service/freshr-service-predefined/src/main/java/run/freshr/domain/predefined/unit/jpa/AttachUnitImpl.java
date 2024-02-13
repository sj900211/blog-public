package run.freshr.domain.predefined.unit.jpa;

import run.freshr.common.annotations.Unit;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.repository.jpa.AttachRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachUnitImpl implements AttachUnit {

  private final AttachRepository repository;

  @Override
  @Transactional
  public Long create(Attach entity) {
    log.info("AttachUnit.create");

    return repository.save(entity).getId();
  }

  @Override
  @Transactional
  public List<Long> create(List<Attach> entities) {
    log.info("AttachUnit.create");

    return repository
        .saveAll(entities)
        .stream()
        .map(Attach::getId)
        .collect(Collectors.toList());
  }

  @Override
  public Boolean exists(Long id) {
    log.info("AttachUnit.exists");

    return repository.existsById(id);
  }

  @Override
  public Attach get(Long id) {
    log.info("AttachUnit.get");

    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

}
