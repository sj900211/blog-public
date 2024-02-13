package run.freshr.domain.conversation.entity.embedded;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ChannelAccountId implements Serializable {

  private Long channelId;
  private Long accountId;

  @Builder
  public ChannelAccountId(Long channelId, Long accountId) {
    log.info("ChannelAccountId.Constructor");

    this.channelId = channelId;
    this.accountId = accountId;
  }

}
