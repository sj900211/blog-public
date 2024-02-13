package run.freshr.domain.blog.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.configurations.DefaultColumnConfig.ZERO;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.enumerations.Visibility;
import run.freshr.common.extensions.entity.EntityAuditLogicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.blog.entity.mapping.PostAttach;
import run.freshr.domain.blog.entity.mapping.PostCommentWard;
import run.freshr.domain.blog.entity.mapping.PostHashtag;
import run.freshr.domain.blog.entity.mapping.PostReaction;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.Hashtag;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_POST",
    indexes = {
        @Index(name = "IDX_BLOG_POST_VISIBILITY", columnList = "visibility"),
        @Index(name = "IDX_BLOG_POST_DEFAULT_FLAG", columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_BLOG_POST_DEFAULT_AT", columnList = "createAt")
    }
)
@AssociationOverrides({
    @AssociationOverride(name = "creator",
        joinColumns = @JoinColumn(name = "creator_id"),
        foreignKey = @ForeignKey(name = "FK_BLOG_POST_CREATOR")),
    @AssociationOverride(name = "updater",
        joinColumns = @JoinColumn(name = "updater_id"),
        foreignKey = @ForeignKey(name = "FK_BLOG_POST_UPDATER"))
})
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 포스팅 관리")
public class Post extends EntityAuditLogicalExtension<Account> {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Column(nullable = false, length = 100)
  @Comment("제목")
  private String title;

  @Lob
  @Column(nullable = false)
  @Comment("내용")
  private String contents;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("공개 유형")
  private Visibility visibility;

  @ColumnDefault(ZERO)
  @Comment("조회수")
  private Integer views;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "blog_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_BLOG_POST_BLOG"))
  @Comment("블로그 일련 번호")
  private Blog blog;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private Set<PostHashtag> hashtagList;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private Set<PostAttach> attachList;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private Set<PostReaction> reactionList;

  @OneToMany(fetch = LAZY, mappedBy = "post")
  private Set<PostCommentWard> wardList;

  @Builder
  public Post(String title, String contents, Visibility visibility, Blog blog) {
    log.info("Post.Constructor");

    this.title = title;
    this.contents = contents;
    this.visibility = visibility;
    this.blog = blog;
  }

  public void updateEntity(String title, String contents, Visibility visibility) {
    log.info("Post.updateEntity");

    this.title = title;
    this.contents = contents;
    this.visibility = visibility;
  }

  public void removeEntity() {
    log.info("Post.removeEntity");

    remove();
  }

  public void increaseViews() {
    this.views++;
  }

  public void addHashtag(Hashtag hashtag) {
    log.info("Post.addHashtag");

    this.hashtagList.add(PostHashtag
        .builder()
        .post(this)
        .hashtag(hashtag)
        .build());
  }

  public void deleteHashtag(Hashtag hashtag) {
    log.info("Post.deleteHashtag");

    this.hashtagList.remove(PostHashtag
        .builder()
        .post(this)
        .hashtag(hashtag)
        .build());
  }

  public void addAttach(Attach attach) {
    log.info("Post.addHashtag");

    this.attachList.add(PostAttach
        .builder()
        .post(this)
        .attach(attach)
        .build());
  }

  public void deleteAttach(Attach attach) {
    log.info("Post.deleteHashtag");

    this.attachList.remove(PostAttach
        .builder()
        .post(this)
        .attach(attach)
        .build());
  }

}
