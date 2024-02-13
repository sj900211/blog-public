package run.freshr.domain.predefined.validator;

import static java.util.Objects.isNull;
import static run.freshr.common.utils.RestUtilAware.field;
import static run.freshr.common.utils.RestUtilAware.rejectRequired;
import static run.freshr.common.utils.RestUtilAware.rejectWrong;
import static run.freshr.domain.predefined.entity.QBasicImage.basicImage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.predefined.vo.BPredefinedStringSearch;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;

@Component
@RequiredArgsConstructor
public class PredefinedValidator {

  public void saveBasicImage(String type, BindingResult bindingResult) {
    BasicImageType basicImageType = BasicImageType.find(type);

    if (isNull(basicImageType)) {
      rejectWrong(field(basicImage.type), bindingResult);
    }
  }

  public boolean getBasicImageList(String type) {
    BasicImageType basicImageType = BasicImageType.find(type);

    return isNull(basicImageType);
  }

  public void getHashtagPage(PredefinedStringSearch search, Errors errors) {
    Integer size = search.getSize();

    if (isNull(size)) {
      rejectRequired(field(BPredefinedStringSearch.size), errors);
    }
  }

}
