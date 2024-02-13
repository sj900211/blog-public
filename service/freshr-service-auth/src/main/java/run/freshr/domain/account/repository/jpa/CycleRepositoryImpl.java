package run.freshr.domain.account.repository.jpa;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CycleRepositoryImpl implements CycleRepository {

  private final EntityManager entityManager;

  @Override
  public Long getCycleSequence() {
    Object sequence = entityManager
        .createNativeQuery("SELECT NEXTVAL('\"account\".seq_cycle')")
        .getSingleResult();

    return Long.valueOf(String.valueOf(sequence));
  }

}
