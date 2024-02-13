package run.freshr.domain.conversation.entity.mapping;

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
import run.freshr.domain.conversation.entity.Channel;
import run.freshr.domain.conversation.entity.embedded.ChannelAccountId;

@Slf4j
@Entity
@Table(
    schema = "conversation",
    name = "CONVERSATION_CHANNEL_ACCOUNT",
    indexes = {
        @Index(name = "IDX_CONVERSATION_CHANNEL_ACCOUNT_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("대화 관리 > 채널 참여자 관리")
public class ChannelAccount extends EntityPhysicalExtension {

  @EmbeddedId
  private ChannelAccountId id;

  @MapsId("channelId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "channel_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_CONVERSATION_CHANNEL_ACCOUNT_CHANNEL"))
  @Comment("채널 정보")
  private Channel channel;

  @MapsId("accountId")
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "account_id", nullable = false,
      foreignKey = @ForeignKey(name = "FK_CONVERSATION_CHANNEL_ACCOUNT_ACCOUNT"))
  @Comment("사용자 정보")
  private Account account;

  @Builder
  public ChannelAccount(Channel channel, Account account) {
    log.info("ChannelAccount.Constructor");

    this.id = ChannelAccountId
        .builder()
        .channelId(channel.getId())
        .accountId(account.getId())
        .build();
    this.channel = channel;
    this.account = account;
  }

}
