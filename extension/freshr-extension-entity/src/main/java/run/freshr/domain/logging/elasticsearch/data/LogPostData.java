package run.freshr.domain.logging.elasticsearch.data;

import static lombok.AccessLevel.PROTECTED;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.common.data.IdDocumentData;
import run.freshr.common.enumerations.Visibility;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LogPostData {

  private String title;

  private String contents;

  @Field(type = FieldType.Text)
  private Visibility visibility;

  private Integer views;

  @Field(type = FieldType.Nested)
  private Set<IdDocumentData<String>> hashtagList;

  @Field(type = FieldType.Nested)
  private Set<IdDocumentData<Long>> attachList;

  @Builder
  public LogPostData(String title, String contents, Visibility visibility, Integer views,
      Set<IdDocumentData<String>> hashtagList, Set<IdDocumentData<Long>> attachList) {
    this.title = title;
    this.contents = contents;
    this.visibility = visibility;
    this.views = views;
    this.hashtagList = hashtagList;
    this.attachList = attachList;
  }

}
