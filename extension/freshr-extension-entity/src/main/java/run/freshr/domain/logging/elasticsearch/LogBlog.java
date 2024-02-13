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
import run.freshr.domain.logging.elasticsearch.data.LogBlogData;
import run.freshr.domain.logging.elasticsearch.extensions.LogExtension;
import run.freshr.domain.logging.enumerations.LogBlogType;

@Slf4j
@Document(indexName = "log-blog")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 로그 관리")
public class LogBlog extends LogExtension<String, LogBlogType, LogBlogData> {

  @Field(type = FieldType.Object)
  @Comment("블로그 일련 번호")
  private IdDocumentData<Long> blog;

  @Builder
  public LogBlog(String id, IdDocumentData<Long> blog, LogBlogType type,
      LogBlogData before, LogBlogData after, IdDocumentData<Long> scribe) {
    log.info("LogBlog.Constructor");

    this.id = id;
    this.blog = blog;
    this.type = type;
    this.before = before;
    this.after = after;
    this.scribe = scribe;
  }

}
