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
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.embedded.BlogSubscribeId;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_SUBSCRIBE",
    indexes = {
        @Index(name = "IDX_BLOG_SUBSCRIBE_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 구독 관리")
public class BlogSubscribe extends EntityPhysicalExtension {

  @EmbeddedId
  private BlogSubscribeId id;

  @MapsId("blogId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_SUBSCRIBE_BLOG"))
  @Comment("블로그 정보")
  private Blog blog;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_SUBSCRIBE_ACCOUNT"))
  @Comment("사용자 정보")
  private Account account;

  @Builder
  public BlogSubscribe(Blog blog, Account account) {
    log.info("BlogSubscribe.Constructor");

    this.id = BlogSubscribeId
        .builder()
        .blogId(blog.getId())
        .accountId(account.getId())
        .build();
    this.blog = blog;
    this.account = account;
  }

}
