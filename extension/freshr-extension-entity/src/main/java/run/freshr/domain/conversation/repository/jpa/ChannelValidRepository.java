package run.freshr.domain.conversation.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.conversation.entity.Channel;

public interface ChannelValidRepository extends JpaRepository<Channel, Long> {

}
