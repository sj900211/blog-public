package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.vo.AccountSearch;

public interface AccountService {

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  ResponseEntity<?> existsAccountByUsername(AccountSearch search);
  ResponseEntity<?> existsAccountByNickname(AccountSearch search);
  ResponseEntity<?> join(AccountJoinRequest dto);
  ResponseEntity<?> getAccountPage(AccountSearch search);
  ResponseEntity<?> getAccount(Long id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    _______   ______    __       __        ______   ____    __    ____
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |   ____| /  __  \  |  |     |  |      /  __  \  \   \  /  \  /   /
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__   |  |  |  | |  |     |  |     |  |  |  |  \   \/    \/   /
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __|  |  |  |  | |  |     |  |     |  |  |  |   \            /
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |     |  `--'  | |  `----.|  `----.|  `--'  |    \    /\    /
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|      \______/  |_______||_______| \______/      \__/  \__/
  ResponseEntity<?> getFollowingList();
  ResponseEntity<?> getFollowerList();
  ResponseEntity<?> existsFollow(Long id);
  ResponseEntity<?> toggleFollow(Long id);

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|
  ResponseEntity<?> getNotificationPage(AccountSearch search);
  ResponseEntity<?> getNotificationByTypePage(AccountSearch search, String type);

}
