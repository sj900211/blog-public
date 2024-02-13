package run.freshr.domain.predefined.unit.jpa;

import java.util.List;
import run.freshr.common.extensions.unit.UnitPageExtension;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;

public interface HashtagUnit extends UnitPageExtension<Hashtag, String, PredefinedStringSearch> {

  List<String> create(List<Hashtag> entities);

  List<Hashtag> search(PredefinedStringSearch search);



}
