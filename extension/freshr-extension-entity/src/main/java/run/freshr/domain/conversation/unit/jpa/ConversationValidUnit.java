package run.freshr.domain.conversation.unit.jpa;

import java.util.Optional;
import run.freshr.domain.conversation.entity.Channel;
import run.freshr.domain.conversation.entity.embedded.ChannelAccountId;
import run.freshr.domain.conversation.entity.mapping.ChannelAccount;

public interface ConversationValidUnit {

  //   ______  __    __       ___      .__   __. .__   __.  _______  __
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|
  long createChannel(Channel entity);

  boolean validateChannel(Long id);

  Optional<Channel> getChannelOptional(Long id);

  Channel getChannel(Long id);

  //   ______  __    __       ___      .__   __. .__   __.  _______  __              ___       ______   ______   ______    __    __  .__   __. .___________.
  //  /      ||  |  |  |     /   \     |  \ |  | |  \ |  | |   ____||  |            /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  // |  ,----'|  |__|  |    /  ^  \    |   \|  | |   \|  | |  |__   |  |           /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  // |  |     |   __   |   /  /_\  \   |  . `  | |  . `  | |   __|  |  |          /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  // |  `----.|  |  |  |  /  _____  \  |  |\   | |  |\   | |  |____ |  `----.    /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  //  \______||__|  |__| /__/     \__\ |__| \__| |__| \__| |_______||_______|   /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  ChannelAccountId createChannelAccount(ChannelAccount entity);

  boolean validateChannelAccount(ChannelAccountId id);

  Optional<ChannelAccount> getChannelAccountOptional(ChannelAccountId id);

  ChannelAccount getChannelAccount(ChannelAccountId id);

}
