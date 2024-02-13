package run.freshr.domain.logging.elasticsearch.data;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.predefined.enumerations.BasicImageType;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LogBasicImageData {

  private Integer sort;

  @Field(type = FieldType.Text)
  private BasicImageType type;

  @Field(type = FieldType.Object)
  private IdDocumentData<Long> image;

  @Builder
  public LogBasicImageData(Integer sort, BasicImageType type, IdDocumentData<Long> image) {
    this.sort = sort;
    this.type = type;
    this.image = image;
  }

}
