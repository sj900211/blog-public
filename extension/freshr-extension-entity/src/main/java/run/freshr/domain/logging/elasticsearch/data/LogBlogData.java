package run.freshr.domain.logging.elasticsearch.data;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.data.elasticsearch.annotations.DateFormat.date;
import static org.springframework.data.elasticsearch.annotations.DateFormat.epoch_millis;

import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.common.data.IdDocumentData;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.enumerations.Visibility;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LogBlogData {

  private String key;

  private String uuid;

  private String title;

  private String description;

  @Field(type = FieldType.Text)
  private Visibility visibility;

  private Boolean coverFlag;

  @Field(type = FieldType.Object)
  private IdDocumentData<Long> cover;

  @Field(type = FieldType.Nested)
  private Set<IdDocumentData<String>> hashtagList;

  @Field(type = FieldType.Nested)
  private Set<IdDocumentData<Long>> participateList;

  @Builder
  public LogBlogData(String key, String uuid, String title, String description,
      Visibility visibility, Boolean coverFlag, IdDocumentData<Long> cover,
      Set<IdDocumentData<String>> hashtagList, Set<IdDocumentData<Long>> participateList) {
    this.key = key;
    this.uuid = uuid;
    this.title = title;
    this.description = description;
    this.visibility = visibility;
    this.coverFlag = coverFlag;
    this.cover = cover;
    this.hashtagList = hashtagList;
    this.participateList = participateList;
  }

}
