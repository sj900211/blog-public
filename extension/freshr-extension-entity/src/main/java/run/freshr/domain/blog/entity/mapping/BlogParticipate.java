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
import run.freshr.common.extensions.entity.EntityPhysicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.embedded.BlogParticipateId;
import run.freshr.domain.blog.enumerations.BlogRole;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_PARTICIPATE",
    indexes = {
        @Index(name = "IDX_BLOG_PARTICIPATE_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 참여자 관리")
public class BlogParticipate extends EntityPhysicalExtension {

  @EmbeddedId
  private BlogParticipateId id;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("권한")
  private BlogRole role;

  @MapsId("blogId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_PARTICIPATE_BLOG"))
  @Comment("블로그 정보")
  private Blog blog;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_PARTICIPATE_ACCOUNT"))
  @Comment("사용자 정보")
  private Account account;

  @Builder
  public BlogParticipate(BlogRole role, Blog blog, Account account) {
    log.info("BlogParticipate.Constructor");

    this.id = BlogParticipateId
        .builder()
        .blogId(blog.getId())
        .accountId(account.getId())
        .build();
    this.role = role;
    this.blog = blog;
    this.account = account;
  }

  public void changeRole(BlogRole role) {
    log.info("BlogParticipate.changeRole");

    this.role = role;
  }

}
