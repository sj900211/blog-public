package run.freshr.controller;

import static run.freshr.common.configurations.URIConfig.uriAccount;
import static run.freshr.common.configurations.URIConfig.uriAccountExistsNickname;
import static run.freshr.common.configurations.URIConfig.uriAccountExistsUsername;
import static run.freshr.common.configurations.URIConfig.uriAccountFollower;
import static run.freshr.common.configurations.URIConfig.uriAccountFollowing;
import static run.freshr.common.configurations.URIConfig.uriAccountId;
import static run.freshr.common.configurations.URIConfig.uriAccountIdFollow;
import static run.freshr.common.configurations.URIConfig.uriAccountIdFollowExists;
import static run.freshr.common.configurations.URIConfig.uriAccountJoin;
import static run.freshr.common.configurations.URIConfig.uriAccountNotification;
import static run.freshr.common.configurations.URIConfig.uriAccountNotificationType;
import static run.freshr.common.utils.RestUtilAware.error;
import static run.freshr.domain.auth.enumerations.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.USER;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.domain.account.dto.request.AccountJoinRequest;
import run.freshr.domain.account.validator.AccountValidator;
import run.freshr.domain.account.vo.AccountSearch;
import run.freshr.service.AccountService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountController {

  private final AccountService service;
  private final AccountValidator validator;

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriAccountExistsUsername)
  public ResponseEntity<?> existsAccountByUsername(@ModelAttribute @Valid AccountSearch search,
      Errors errors) {
    log.info("AccountController.existsAccountByUsername");

    validator.existsAccountByUsername(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.existsAccountByUsername(search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriAccountExistsNickname)
  public ResponseEntity<?> existsAccountByNickname(@ModelAttribute @Valid AccountSearch search,
      Errors errors) {
    log.info("AccountController.existsAccountByNickname");

    validator.existsAccountByNickname(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.existsAccountByNickname(search);
  }

  @Secured(ANONYMOUS)
  @PostMapping(uriAccountJoin)
  public ResponseEntity<?> join(@RequestBody @Valid AccountJoinRequest dto,
      BindingResult bindingResult) {
    log.info("AccountController.join");

    validator.join(dto, bindingResult);

    if (bindingResult.hasErrors()) {
      return error(bindingResult);
    }

    return service.join(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriAccount)
  public ResponseEntity<?> getAccountPage(@ModelAttribute @Valid AccountSearch search,
      Errors errors) {
    log.info("AccountController.getAccountPage");

    validator.getAccountPage(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.getAccountPage(search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriAccountId)
  public ResponseEntity<?> getAccount(@PathVariable Long id) {
    log.info("AccountController.getAccount");

    return service.getAccount(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    _______   ______    __       __        ______   ____    __    ____
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |   ____| /  __  \  |  |     |  |      /  __  \  \   \  /  \  /   /
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__   |  |  |  | |  |     |  |     |  |  |  |  \   \/    \/   /
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __|  |  |  |  | |  |     |  |     |  |  |  |   \            /
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |     |  `--'  | |  `----.|  `----.|  `--'  |    \    /\    /
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|      \______/  |_______||_______| \______/      \__/  \__/
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriAccountFollowing)
  public ResponseEntity<?> getFollowingList() {
    log.info("AccountController.getFollowingList");

    return service.getFollowingList();
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriAccountFollower)
  public ResponseEntity<?> getFollowerList() {
    log.info("AccountController.getFollowerList");

    return service.getFollowerList();
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriAccountIdFollowExists)
  public ResponseEntity<?> existsFollow(@PathVariable Long id) {
    log.info("AccountController.existsFollow");

    return service.existsFollow(id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PostMapping(uriAccountIdFollow)
  public ResponseEntity<?> toggleFollow(@PathVariable Long id) {
    log.info("AccountController.toggleFollow");

    return service.toggleFollow(id);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriAccountNotification)
  public ResponseEntity<?> getNotificationPage(@ModelAttribute @Valid AccountSearch search) {
    log.info("AccountController.getNotificationPage");

    return service.getNotificationPage(search);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriAccountNotificationType)
  public ResponseEntity<?> getNotificationByTypePage(@PathVariable String type,
      @ModelAttribute @Valid AccountSearch search, Errors errors) {
    log.info("AccountController.getNotificationByTypePage");

    validator.getNotificationByTypePage(search, type, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.getNotificationByTypePage(search, type);
  }

}
