package run.freshr.domain.conversation.unit.jpa;

import static run.freshr.common.utils.EntityValidateUtil.validateLogical;
import static run.freshr.common.utils.EntityValidateUtil.validatePhysical;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.conversation.entity.Channel;
import run.freshr.domain.conversation.entity.embedded.ChannelAccountId;
import run.freshr.domain.conversation.entity.mapping.ChannelAccount;
import run.freshr.domain.conversation.repository.jpa.ChannelAccountValidRepository;
import run.freshr.domain.conversation.repository.jpa.ChannelValidRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConversationValidUnitImpl implements ConversationValidUnit {

  //   ______  __    __       ___      .__   __. .__   __.  _______  __
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|
  private final ChannelValidRepository channelValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public long createChannel(Channel entity) {
    return channelValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateChannel(Long id) {
    return validateLogical(channelValidRepository.findById(id));
  }

  @Override
  public Optional<Channel> getChannelOptional(Long id) {
    return channelValidRepository.findById(id);
  }

  @Override
  public Channel getChannel(Long id) {
    return channelValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //   ______  __    __       ___      .__   __. .__   __.  _______  __              ___       ______   ______   ______    __    __  .__   __. .___________.
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |            /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |           /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |          /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.    /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|   /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  private final ChannelAccountValidRepository channelAccountValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public ChannelAccountId createChannelAccount(ChannelAccount entity) {
    return channelAccountValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateChannelAccount(ChannelAccountId id) {
    return validatePhysical(channelAccountValidRepository.findById(id));
  }

  @Override
  public Optional<ChannelAccount> getChannelAccountOptional(ChannelAccountId id) {
    return channelAccountValidRepository.findById(id);
  }

  @Override
  public ChannelAccount getChannelAccount(ChannelAccountId id) {
    return channelAccountValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
