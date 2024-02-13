package run.freshr.domain.conversation.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.conversation.entity.embedded.ChannelAccountId;
import run.freshr.domain.conversation.entity.mapping.ChannelAccount;

public interface ChannelAccountValidRepository extends
    JpaRepository<ChannelAccount, ChannelAccountId> {

}
