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
import run.freshr.domain.logging.elasticsearch.data.LogPostCommentData;
import run.freshr.domain.logging.elasticsearch.extensions.LogExtension;
import run.freshr.domain.logging.enumerations.LogPostCommentType;

@Slf4j
@Document(indexName = "log-post-comment")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Comment("포스팅 댓글 로그 관리")
public class LogPostComment extends LogExtension<String, LogPostCommentType, LogPostCommentData> {

  @Field(type = FieldType.Object)
  @Comment("포스팅 일련 번호")
  private IdDocumentData<Long> post;

  @Field(type = FieldType.Object)
  @Comment("포스팅 댓글 일련 번호")
  private IdDocumentData<Long> postComment;

  @Builder
  public LogPostComment(String id, IdDocumentData<Long> post, IdDocumentData<Long> postComment,
      LogPostCommentType type, LogPostCommentData before, LogPostCommentData after,
      IdDocumentData<Long> scribe) {
    log.info("LogPostComment.Constructor");

    this.id = id;
    this.post = post;
    this.postComment = postComment;
    this.type = type;
    this.before = before;
    this.after = after;
    this.scribe = scribe;
  }

}
