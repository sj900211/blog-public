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
import run.freshr.domain.logging.elasticsearch.data.LogPostData;
import run.freshr.domain.logging.elasticsearch.extensions.LogExtension;
import run.freshr.domain.logging.enumerations.LogPostType;

@Slf4j
@Document(indexName = "log-post")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Comment("포스팅 로그 관리")
public class LogPost extends LogExtension<String, LogPostType, LogPostData> {

  @Field(type = FieldType.Object)
  @Comment("블로그 일련 번호")
  private IdDocumentData<Long> blog;

  @Field(type = FieldType.Object)
  @Comment("포스팅 일련 번호")
  private IdDocumentData<Long> post;

  @Builder
  public LogPost(String id, IdDocumentData<Long> blog, IdDocumentData<Long> post,
      LogPostType type, LogPostData before, LogPostData after, IdDocumentData<Long> scribe) {
    log.info("LogPost.Constructor");

    this.id = id;
    this.blog = blog;
    this.post = post;
    this.type = type;
    this.before = before;
    this.after = after;
    this.scribe = scribe;
  }

}
