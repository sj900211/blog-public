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
import run.freshr.domain.blog.entity.embedded.PostHashtagId;
import run.freshr.domain.predefined.entity.Hashtag;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_POST_HASHTAG",
    indexes = {
        @Index(name = "IDX_BLOG_POST_HASHTAG_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 포스팅 해시태그 관리")
public class PostHashtag extends EntityPhysicalExtension {

  @EmbeddedId
  private PostHashtagId id;

  @MapsId("postId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_HASHTAG_POST"))
  @Comment("포스팅 정보")
  private Post post;

  @MapsId("hashtagId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "hashtag_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_HASHTAG_HASHTAG"))
  @Comment("해시태그 정보")
  private Hashtag hashtag;

  @Builder
  public PostHashtag(Post post, Hashtag hashtag) {
    log.info("PostHashtag.Constructor");

    this.id = PostHashtagId
        .builder()
        .postId(post.getId())
        .hashtagId(hashtag.getId())
        .build();
    this.post = post;
    this.hashtag = hashtag;
  }

}
