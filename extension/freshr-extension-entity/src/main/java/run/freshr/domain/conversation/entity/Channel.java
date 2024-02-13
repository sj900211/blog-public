package run.freshr.domain.conversation.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extensions.entity.EntityLogicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.conversation.entity.mapping.ChannelAccount;
import run.freshr.domain.conversation.enumerations.ChannelType;

@Slf4j
@Entity
@Table(
    schema = "conversation",
    name = "CONVERSATION_CHANNEL",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_CONVERSATION_CHANNEL_UUID", columnNames = {"uuid"})
    },
    indexes = {
        @Index(name = "IDX_CONVERSATION_CHANNEL_DEFAULT_FLAG", columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_CONVERSATION_CHANNEL_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("대화 관리 > 채널 관리")
public class Channel extends EntityLogicalExtension {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("유형")
  private ChannelType type;

  @Column(nullable = false)
  @Comment("아이디 - 고유 아이디")
  private String uuid;

  @OneToMany(fetch = LAZY, mappedBy = "channel")
  private List<ChannelAccount> accountList;

  @Builder
  public Channel(ChannelType type, String uuid) {
    log.info("Channel.Constructor");

    this.type = type;
    this.uuid = uuid;
  }

  public void removeEntity() {
    log.info("Channel.removeEntity");

    remove();
  }

  public void addAccount(Account account) {
    log.info("Channel.addAccount");

    this.accountList.add(ChannelAccount
        .builder()
        .channel(this)
        .account(account)
        .build());
  }

  public void deleteAccount(Account account) {
    log.info("Channel.deleteAccount");

    this.accountList.remove(ChannelAccount
        .builder()
        .channel(this)
        .account(account)
        .build());
  }

}
