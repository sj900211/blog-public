package run.freshr.domain.logging.elasticsearch.extensions;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date_hour_minute_second_millis;
import static org.springframework.data.elasticsearch.annotations.DateFormat.epoch_millis;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.common.data.IdDocumentData;

@Getter
public class LogExtension<ID, TYPE, DATA> {

  @Id
  @Comment("일련 번호")
  protected ID id;

  @Field(type = FieldType.Text)
  protected TYPE type;

  @Field(type = FieldType.Object)
  @Comment("변경 전 데이터")
  protected DATA before;

  @Field(type = FieldType.Object)
  @Comment("변경 후 데이터")
  protected DATA after;

  @CreatedDate
  @Field(type = FieldType.Date, format = {date_hour_minute_second_millis, epoch_millis})
  @Comment("날짜 시간")
  protected LocalDateTime timestamp;

  @Field(type = FieldType.Object)
  @Comment("필경사 일련 번호 - 로그 작성 액션을 실행한 사용자의 일련 번호")
  protected IdDocumentData<Long> scribe;

}
