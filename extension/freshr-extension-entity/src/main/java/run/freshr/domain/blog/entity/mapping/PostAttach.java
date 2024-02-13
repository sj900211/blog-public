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
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.entity.embedded.PostAttachId;
import run.freshr.domain.predefined.entity.Attach;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_POST_ATTACH",
    indexes = {
        @Index(name = "IDX_BLOG_POST_ATTACH_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 포스팅 첨부파일 관리")
public class PostAttach extends EntityPhysicalExtension {

  @EmbeddedId
  private PostAttachId id;

  @MapsId("postId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_ATTACH_POST"))
  @Comment("포스팅 정보")
  private Post post;

  @MapsId("attachId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "attach_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_ATTACH_ATTACH"))
  @Comment("첨부파일 정보")
  private Attach attach;

  @Builder
  public PostAttach(Post post, Attach attach) {
    log.info("PostAttach.Constructor");

    this.id = PostAttachId
        .builder()
        .postId(post.getId())
        .attachId(attach.getId())
        .build();
    this.post = post;
    this.attach = attach;
  }

}
