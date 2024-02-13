package run.freshr.domain.account.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.domain.account.enumerations.AccountStatus.ACTIVE;
import static run.freshr.domain.account.enumerations.AccountStatus.DORMANT;
import static run.freshr.domain.account.enumerations.AccountStatus.WITHDRAWAL;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.entity.EntityLogicalExtension;
import run.freshr.domain.account.entity.mapping.AccountFollow;
import run.freshr.domain.account.entity.mapping.AccountHashtag;
import run.freshr.domain.account.enumerations.AccountStatus;
import run.freshr.domain.auth.enumerations.Privilege;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.Hashtag;

@Slf4j
@Entity
@Table(
    schema = "account",
    name = "ACCOUNT_INFO",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_ACCOUNT_INFO_UUID", columnNames = {"uuid"}),
        @UniqueConstraint(name = "UK_ACCOUNT_INFO_USERNAME", columnNames = {"username"}),
        @UniqueConstraint(name = "UK_ACCOUNT_INFO_NICKNAME", columnNames = {"nickname"})
    },
    indexes = {
        @Index(name = "IDX_ACCOUNT_INFO_PRIVILEGE", columnList = "privilege"),
        @Index(name = "IDX_ACCOUNT_INFO_DEFAULT_FLAG", columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_ACCOUNT_INFO_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("사용자 관리 > 계정 관리")
public class Account extends EntityLogicalExtension {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("상태")
  private AccountStatus status;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("권한")
  private Privilege privilege;

  @Column(nullable = false)
  @Comment("아이디 - 고유 아이디")
  private String uuid;

  @Column(nullable = false)
  @Comment("아이디 - 이메일")
  private String username;

  @Comment("비밀번호")
  private String password;

  @Comment("이전 비밀번호")
  private String previousPassword;

  @Comment("닉네임")
  private String nickname;

  @Comment("소개")
  private String introduce;

  @Enumerated(STRING)
  @Comment("성별")
  private Gender gender;

  @Comment("생일")
  private LocalDate birth;

  @Comment("최근 접속 날짜 시간")
  private LocalDateTime signAt;

  @Comment("탈퇴 날짜 시간")
  private LocalDateTime removeAt;

  @ManyToOne(fetch = LAZY)
  @Comment("프로필 이미지 정보")
  @JoinColumn(name = "profile_id", foreignKey = @ForeignKey(name = "FK_ACCOUNT_INFO_PROFILE"))
  private Attach profile;

  @OneToMany(fetch = LAZY, mappedBy = "following")
  private List<AccountFollow> followerList = new ArrayList<>();

  @OneToMany(fetch = LAZY, mappedBy = "follower")
  private List<AccountFollow> followingList = new ArrayList<>();

  @OneToMany(fetch = LAZY, mappedBy = "account")
  private Set<AccountHashtag> hashtagList = new HashSet<>();
  ;

  @Builder
  public Account(String uuid, Privilege privilege, String username, String password,
      String nickname, String introduce, Gender gender, LocalDate birth, Attach profile) {
    log.info("Account.Constructor");

    this.privilege = privilege;
    this.status = ACTIVE;
    this.uuid = uuid;
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.introduce = introduce;
    this.gender = gender;
    this.birth = birth;
    this.profile = profile;
  }

  public void updateEntity(String nickname, String introduce, Gender gender, LocalDate birth,
      Attach profile) {
    log.info("Account.updateEntity");

    this.nickname = nickname;
    this.introduce = introduce;
    this.gender = gender;
    this.birth = birth;
    this.profile = profile;
  }

  public void signed() {
    log.info("Account.signed");

    this.signAt = now();
  }

  public void changePassword(String password) {
    log.info("Account.changePassword");

    this.previousPassword = this.password;
    this.password = password;
  }

  public void changePrivilege(Privilege privilege) {
    log.info("Account.changePrivilege");

    this.privilege = privilege;
  }

  public void dormant() {
    log.info("Account.dormant");

    this.status = DORMANT;
    this.password = null;
    this.nickname = null;
    this.gender = null;
    this.birth = null;
  }

  public void withdrawal() {
    log.info("Account.withdrawal");

    this.username = this.username + "-" + now().format(ofPattern("yyyyMMddHHmmss"));
    this.removeAt = now();
    this.status = WITHDRAWAL;
    this.password = null;
    this.nickname = null;
    this.gender = null;
    this.birth = null;

    remove();
  }

  public void addHashtag(Hashtag hashtag) {
    log.info("Account.addHashtag");

    this.hashtagList.add(AccountHashtag
        .builder()
        .account(this)
        .hashtag(hashtag)
        .build());
  }

  public void deleteHashtag(Hashtag hashtag) {
    log.info("Account.deleteHashtag");

    this.hashtagList.remove(AccountHashtag
        .builder()
        .account(this)
        .hashtag(hashtag)
        .build());
  }

}
