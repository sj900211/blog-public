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
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.embedded.BlogHashtagId;
import run.freshr.domain.predefined.entity.Hashtag;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_INFO_HASHTAG",
    indexes = {
        @Index(name = "IDX_BLOG_INFO_HASHTAG_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 블로그 해시태그 관리")
public class BlogHashtag extends EntityPhysicalExtension {

  @EmbeddedId
  private BlogHashtagId id;

  @MapsId("blogId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_INFO_HASHTAG_BLOG"))
  @Comment("블로그 정보")
  private Blog blog;

  @MapsId("hashtagId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "hashtag_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_INFO_HASHTAG_HASHTAG"))
  @Comment("해시태그 정보")
  private Hashtag hashtag;

  @Builder
  public BlogHashtag(Blog blog, Hashtag hashtag) {
    log.info("BlogHashtag.Constructor");

    this.id = BlogHashtagId
        .builder()
        .blogId(blog.getId())
        .hashtagId(hashtag.getId())
        .build();
    this.blog = blog;
    this.hashtag = hashtag;
  }

}
