package run.freshr.domain.blog.entity.mapping;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
import run.freshr.common.extensions.entity.EntityPhysicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.entity.embedded.PostCommentWardId;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_POST_COMMENT_WARD",
    indexes = {
        @Index(name = "IDX_BLOG_POST_COMMENT_WARD_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 포스팅 댓글 알림 수신 관리")
public class PostCommentWard extends EntityPhysicalExtension {

  @EmbeddedId
  private PostCommentWardId id;

  @MapsId("postId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_COMMENT_WARD_POST"))
  @Comment("포스팅 정보")
  private Post post;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_COMMENT_WARD_ACCOUNT"))
  @Comment("사용자 정보")
  private Account account;

  @Builder
  public PostCommentWard(Post post, Account account) {
    log.info("PostCommentWard.Constructor");

    this.id = PostCommentWardId
        .builder()
        .postId(post.getId())
        .accountId(account.getId())
        .build();
    this.post = post;
    this.account = account;
  }

}
