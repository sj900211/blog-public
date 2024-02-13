package run.freshr.domain.account.unit.jpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.account.repository.jpa.CycleRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CycleUnitImpl implements CycleUnit {

  private final CycleRepository repository;

  @Override
  public Long getCycleSequence() {
    return repository.getCycleSequence();
  }

}
