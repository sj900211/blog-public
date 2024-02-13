package run.freshr.domain.logging.elasticsearch;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogBasicImageData;
import run.freshr.domain.logging.elasticsearch.extensions.LogExtension;
import run.freshr.domain.logging.enumerations.LogBasicImageType;

@Slf4j
@Document(indexName = "log-basic-image")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Comment("기본 이미지 로그 관리")
public class LogBasicImage extends LogExtension<String, LogBasicImageType, LogBasicImageData> {

  @Field(type = FieldType.Object)
  @Comment("기본 이미지 일련 번호")
  private IdDocumentData<Long> basicImage;

  @Builder
  public LogBasicImage(String id, IdDocumentData<Long> basicImage, LogBasicImageType type,
      LogBasicImageData before, LogBasicImageData after, IdDocumentData<Long> scribe) {
    log.info("LogBasicImage.Constructor");

    this.id = id;
    this.basicImage = basicImage;
    this.type = type;
    this.before = before;
    this.after = after;
    this.scribe = scribe;
  }

}
