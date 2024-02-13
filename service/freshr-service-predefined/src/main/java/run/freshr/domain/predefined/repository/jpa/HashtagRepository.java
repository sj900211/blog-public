package run.freshr.domain.predefined.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.predefined.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, String>,
    HashtagRepositoryCustom {

}
