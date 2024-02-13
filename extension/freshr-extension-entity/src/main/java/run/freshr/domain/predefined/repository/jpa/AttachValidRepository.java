package run.freshr.domain.predefined.repository.jpa;

import run.freshr.domain.predefined.entity.Attach;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachValidRepository extends JpaRepository<Attach, Long> {

  Optional<Attach> findByKey(String key);

}
