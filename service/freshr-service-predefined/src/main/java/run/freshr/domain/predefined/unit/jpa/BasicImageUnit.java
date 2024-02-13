package run.freshr.domain.predefined.unit.jpa;

import java.util.List;
import run.freshr.common.extensions.unit.UnitDefaultExtension;
import run.freshr.domain.predefined.entity.BasicImage;
import run.freshr.domain.predefined.enumerations.BasicImageType;

public interface BasicImageUnit extends UnitDefaultExtension<BasicImage, Long> {

  List<Long> create(List<BasicImage> entities);

  List<BasicImage> getList(BasicImageType type);

  void deleteAll(BasicImageType type);

}
