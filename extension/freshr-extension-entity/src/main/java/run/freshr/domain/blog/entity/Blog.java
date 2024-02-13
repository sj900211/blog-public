package run.freshr.domain.blog.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.enumerations.Visibility;
import run.freshr.common.extensions.entity.EntityAuditLogicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.blog.entity.mapping.BlogHashtag;
import run.freshr.domain.blog.entity.mapping.BlogParticipate;
import run.freshr.domain.blog.entity.mapping.BlogParticipateRequest;
import run.freshr.domain.blog.entity.mapping.BlogSubscribe;
import run.freshr.domain.conversation.entity.Channel;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.Hashtag;

@Slf4j
@Entity
@Table(
    schema = "blog",
    name = "BLOG_INFO",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_BLOG_INFO_UUID", columnNames = {"uuid"}),
        @UniqueConstraint(name = "UK_BLOG_INFO_KEY", columnNames = {"key"})
    },
    indexes = {
        @Index(name = "IDX_BLOG_INFO_VISIBILITY", columnList = "visibility"),
        @Index(name = "IDX_BLOG_INFO_DEFAULT_FLAG", columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_BLOG_INFO_DEFAULT_AT", columnList = "createAt")
    }
)
@AssociationOverrides({
    @AssociationOverride(name = "creator",
        joinColumns = @JoinColumn(name = "creator_id"),
        foreignKey = @ForeignKey(name = "FK_BLOG_INFO_CREATOR")),
    @AssociationOverride(name = "updater",
        joinColumns = @JoinColumn(name = "updater_id"),
        foreignKey = @ForeignKey(name = "FK_BLOG_INFO_UPDATER"))
})
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("블로그 관리 > 블로그 정보 관리")
public class Blog extends EntityAuditLogicalExtension<Account> {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Column(nullable = false, length = 20)
  @Comment("키 - 고유")
  private String key;

  @Column(nullable = false)
  @Comment("아이디 - 고유 아이디")
  private String uuid;

  @Column(nullable = false, length = 100)
  @Comment("제목")
  private String title;

  @Column(nullable = false)
  @Comment("설명")
  private String description;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("공개 유형")
  private Visibility visibility;

  @Column(nullable = false)
  @Comment("커버 사용 여부")
  private Boolean coverFlag;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "cover_id", foreignKey = @ForeignKey(name = "FK_BLOG_INFO_COVER"))
  @Comment("커버 이미지")
  private Attach cover;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "channel_id", foreignKey = @ForeignKey(name = "FK_BLOG_INFO_CHANNEL"))
  @Comment("채널")
  private Channel channel;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private Set<BlogHashtag> hashtagList;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private Set<BlogParticipate> participateList;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private Set<BlogParticipateRequest> participateRequestList;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private Set<BlogSubscribe> subscribeList;

  @OneToMany(fetch = LAZY, mappedBy = "blog")
  private List<Post> postList;

  @Builder
  public Blog(String key, String uuid, String title, String description, Visibility visibility,
      Boolean coverFlag, Attach cover, Channel channel) {
    log.info("Blog.Constructor");

    this.key = key;
    this.uuid = uuid;
    this.title = title;
    this.description = description;
    this.visibility = ofNullable(visibility).orElse(Visibility.PUBLIC);
    this.coverFlag = coverFlag;
    this.channel = channel;

    if (coverFlag && !isNull(cover)) {
      this.cover = cover;
    }
  }

  public void updateEntity(String key, String title, String description, Visibility visibility,
      Boolean coverFlag, Attach cover) {
    log.info("Blog.updateEntity");

    this.key = key;
    this.title = title;
    this.description = description;
    this.visibility = ofNullable(visibility).orElse(Visibility.PUBLIC);
    this.coverFlag = coverFlag;

    if (coverFlag && !isNull(cover)) {
      this.cover = cover;
    }
  }

  public void removeEntity() {
    log.info("Blog.removeEntity");

    remove();
  }

  public void addHashtag(Hashtag hashtag) {
    log.info("Blog.addHashtag");

    this.hashtagList.add(BlogHashtag
        .builder()
        .blog(this)
        .hashtag(hashtag)
        .build());
  }

  public void deleteHashtag(Hashtag hashtag) {
    log.info("Blog.deleteHashtag");

    this.hashtagList.remove(BlogHashtag
        .builder()
        .blog(this)
        .hashtag(hashtag)
        .build());
  }

}
