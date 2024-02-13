package run.freshr.domain.predefined.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.freshr.common.annotations.SearchClass;
import run.freshr.common.extensions.request.SearchExtension;

@Data
@SearchClass
@EqualsAndHashCode(callSuper = true)
public class PredefinedStringSearch extends SearchExtension<String> {

}
