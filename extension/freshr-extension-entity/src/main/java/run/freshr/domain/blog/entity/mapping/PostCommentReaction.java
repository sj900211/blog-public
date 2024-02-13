package run.freshr.domain.blog.entity.mapping;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.enumerations.Reaction;
import run.freshr.common.extensions.entity.EntityPhysicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.blog.entity.PostComment;
import run.freshr.domain.blog.entity.embedded.PostCommentReactionId;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_POST_COMMENT_REACTION",
    indexes = {
        @Index(name = "IDX_BLOG_POST_COMMENT_REACTION_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 포스팅 댓글 반응 관리")
public class PostCommentReaction extends EntityPhysicalExtension {

  @EmbeddedId
  private PostCommentReactionId id;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("반응")
  private Reaction reaction;

  @MapsId("postCommentId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_comment_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_COMMENT_REACTION_POST_COMMENT"))
  @Comment("포스팅 댓글 정보")
  private PostComment postComment;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_COMMENT_REACTION_ACCOUNT"))
  @Comment("사용자 정보")
  private Account account;

  @Builder
  public PostCommentReaction(PostComment postComment, Account account, Reaction reaction) {
    log.info("PostCommentReaction.Constructor");

    this.id = PostCommentReactionId
        .builder()
        .postCommentId(postComment.getId())
        .accountId(account.getId())
        .build();
    this.postComment = postComment;
    this.account = account;
    this.reaction = reaction;
  }

}
