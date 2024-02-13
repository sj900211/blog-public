package run.freshr.domain.blog.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extensions.entity.EntityAuditLogicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.blog.entity.mapping.PostCommentReaction;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_POST_COMMENT",
    indexes = {
        @Index(name = "IDX_BLOG_POST_COMMENT_DEFAULT_FLAG", columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_BLOG_POST_COMMENT_DEFAULT_AT", columnList = "createAt")
    }
)
@AssociationOverrides({
    @AssociationOverride(name = "creator",
        joinColumns = @JoinColumn(name = "creator_id"),
        foreignKey = @ForeignKey(name = "FK_BLOG_POST_COMMENT_CREATOR")),
    @AssociationOverride(name = "updater",
        joinColumns = @JoinColumn(name = "updater_id"),
        foreignKey = @ForeignKey(name = "FK_BLOG_POST_COMMENT_UPDATER"))
})
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 포스팅 댓글 관리")
public class PostComment extends EntityAuditLogicalExtension<Account> {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Column(nullable = false)
  @Comment("내용")
  private String contents;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "post_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_COMMENT_BLOG"))
  @Comment("포스팅 일련 번호")
  private Post post;

  @OneToMany(fetch = LAZY, mappedBy = "postComment")
  private Set<PostCommentReaction> reactionList;

  @Builder
  public PostComment(String contents, Post post) {
    log.info("PostComment.Constructor");

    this.contents = contents;
    this.post = post;
  }

  public void updateEntity(String contents) {
    log.info("PostComment.updateEntity");

    this.contents = contents;
  }

  public void removeEntity() {
    log.info("PostComment.removeEntity");

    remove();
  }

}
