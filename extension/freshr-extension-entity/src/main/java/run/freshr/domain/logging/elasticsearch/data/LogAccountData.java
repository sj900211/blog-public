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

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LogAccountData {

  private String nickname;

  private String introduce;

  @Field(type = FieldType.Text)
  private Gender gender;

  @Field(type = FieldType.Date, format = {date, epoch_millis})
  private LocalDate birth;

  @Field(type = FieldType.Object)
  private IdDocumentData<Long> profile;

  @Field(type = FieldType.Nested)
  private Set<IdDocumentData<String>> hashtagList;

  @Builder
  public LogAccountData(String nickname, String introduce, Gender gender, LocalDate birth,
      IdDocumentData<Long> profile, Set<IdDocumentData<String>> hashtagList) {
    this.nickname = nickname;
    this.introduce = introduce;
    this.gender = gender;
    this.birth = birth;
    this.profile = profile;
    this.hashtagList = hashtagList;
  }

}
