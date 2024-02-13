package run.freshr.domain.predefined.repository.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.predefined.entity.BasicImage;
import run.freshr.domain.predefined.enumerations.BasicImageType;

public interface BasicImageRepository extends JpaRepository<BasicImage, Long> {

  List<BasicImage> findAllByTypeOrderBySortAscIdDesc(BasicImageType type);

  void deleteAllByType(BasicImageType type);

}
