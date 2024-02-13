package run.freshr.domain.predefined.repository.jpa;

import java.util.List;
import run.freshr.common.data.CursorData;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;

public interface HashtagRepositoryCustom {

  List<Hashtag> search(PredefinedStringSearch search);

  CursorData<Hashtag> getPage(PredefinedStringSearch search);

}
