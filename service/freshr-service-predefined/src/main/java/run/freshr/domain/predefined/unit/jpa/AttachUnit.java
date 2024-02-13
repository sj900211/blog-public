package run.freshr.domain.predefined.unit.jpa;

import run.freshr.common.extensions.unit.UnitDefaultExtension;
import run.freshr.domain.predefined.entity.Attach;
import java.util.List;

public interface AttachUnit extends UnitDefaultExtension<Attach, Long> {

  List<Long> create(List<Attach> entities);

}
