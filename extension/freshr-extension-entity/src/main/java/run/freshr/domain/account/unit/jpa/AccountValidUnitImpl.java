package run.freshr.domain.account.unit.jpa;

import static run.freshr.common.utils.EntityValidateUtil.validateAuditorPhysical;
import static run.freshr.common.utils.EntityValidateUtil.validateLogical;
import static run.freshr.common.utils.EntityValidateUtil.validatePhysical;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.account.entity.embedded.AccountFollowId;
import run.freshr.domain.account.entity.embedded.AccountHashtagId;
import run.freshr.domain.account.entity.inherit.AccountNotificationAccount;
import run.freshr.domain.account.entity.inherit.AccountNotificationBlog;
import run.freshr.domain.account.entity.inherit.AccountNotificationPost;
import run.freshr.domain.account.entity.inherit.AccountNotificationPostComment;
import run.freshr.domain.account.entity.mapping.AccountFollow;
import run.freshr.domain.account.entity.mapping.AccountHashtag;
import run.freshr.domain.account.repository.jpa.AccountFollowValidRepository;
import run.freshr.domain.account.repository.jpa.AccountHashtagValidRepository;
import run.freshr.domain.account.repository.jpa.AccountNotificationAccountValidRepository;
import run.freshr.domain.account.repository.jpa.AccountNotificationBlogValidRepository;
import run.freshr.domain.account.repository.jpa.AccountNotificationPostCommentValidRepository;
import run.freshr.domain.account.repository.jpa.AccountNotificationPostValidRepository;
import run.freshr.domain.account.repository.jpa.AccountValidRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountValidUnitImpl implements AccountValidUnit {

  //      ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  private final AccountValidRepository accountValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public long createAccount(Account entity) {
    return accountValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAccount(Long id) {
    return validateLogical(accountValidRepository.findById(id));
  }

  @Override
  public Optional<Account> getAccountOptional(Long id) {
    return accountValidRepository.findById(id);
  }

  @Override
  public Account getAccount(Long id) {
    return accountValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    _______   ______    __       __        ______   ____    __    ____
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |   ____| /  __  \  |  |     |  |      /  __  \  \   \  /  \  /   /
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__   |  |  |  | |  |     |  |     |  |  |  |  \   \/    \/   /
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __|  |  |  |  | |  |     |  |     |  |  |  |   \            /
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |     |  `--'  | |  `----.|  `----.|  `--'  |    \    /\    /
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|      \______/  |_______||_______| \______/      \__/  \__/
  private final AccountFollowValidRepository accountFollowValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public AccountFollowId createAccountFollow(AccountFollow entity) {
    return accountFollowValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAccountFollow(AccountFollowId id) {
    return validatePhysical(accountFollowValidRepository.findById(id));
  }

  @Override
  public Optional<AccountFollow> getAccountFollowOptional(AccountFollowId id) {
    return accountFollowValidRepository.findById(id);
  }

  @Override
  public AccountFollow getAccountFollow(AccountFollowId id) {
    return accountFollowValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.    __    __       ___           _______. __    __  .___________.    ___       _______
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  private final AccountHashtagValidRepository accountHashtagValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public AccountHashtagId createAccountHashtag(AccountHashtag entity) {
    return accountHashtagValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAccountHashtag(AccountHashtagId id) {
    return validatePhysical(accountHashtagValidRepository.findById(id));
  }

  @Override
  public Optional<AccountHashtag> getAccountHashtagOptional(AccountHashtagId id) {
    return accountHashtagValidRepository.findById(id);
  }

  @Override
  public AccountHashtag getAccountHashtag(AccountHashtagId id) {
    return accountHashtagValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.         ___       ______   ______   ______    __    __  .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |        /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |       /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |      /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |     /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|
  private final AccountNotificationAccountValidRepository accountNotificationAccountValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public String createAccountNotificationAccount(AccountNotificationAccount entity) {
    return accountNotificationAccountValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAccountNotificationAccount(String id) {
    return validateAuditorPhysical(accountNotificationAccountValidRepository.findById(id));
  }

  @Override
  public Optional<AccountNotificationAccount> getAccountNotificationAccountOptional(String id) {
    return accountNotificationAccountValidRepository.findById(id);
  }

  @Override
  public AccountNotificationAccount getAccountNotificationAccount(String id) {
    return accountNotificationAccountValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______    __        ______     _______
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \  |  |      /  __  \   /  _____|
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |     |  |  |  | |  |  __
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   _  <  |  |     |  |  |  | |  | |_ |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |_)  | |  `----.|  `--'  | |  |__| |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    |______/  |_______| \______/   \______|
  private final AccountNotificationBlogValidRepository accountNotificationBlogValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public String createAccountNotificationBlog(AccountNotificationBlog entity) {
    return accountNotificationBlogValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAccountNotificationBlog(String id) {
    return validateAuditorPhysical(accountNotificationBlogValidRepository.findById(id));
  }

  @Override
  public Optional<AccountNotificationBlog> getAccountNotificationBlogOptional(String id) {
    return accountNotificationBlogValidRepository.findById(id);
  }

  @Override
  public AccountNotificationBlog getAccountNotificationBlog(String id) {
    return accountNotificationBlogValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______     ______        _______.___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \   /  __  \      /       |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |  |  |    |   (----`---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   ___/  |  |  |  |     \   \       |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |      |  `--'  | .----)   |      |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    | _|       \______/  |_______/       |__|
  private final AccountNotificationPostValidRepository accountNotificationPostValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public String createAccountNotificationPost(AccountNotificationPost entity) {
    return accountNotificationPostValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAccountNotificationPost(String id) {
    return validateAuditorPhysical(accountNotificationPostValidRepository.findById(id));
  }

  @Override
  public Optional<AccountNotificationPost> getAccountNotificationPostOptional(String id) {
    return accountNotificationPostValidRepository.findById(id);
  }

  @Override
  public AccountNotificationPost getAccountNotificationPost(String id) {
    return accountNotificationPostValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  //      ___       ______   ______   ______    __    __  .__   __. .___________.   .__   __.   ______   .___________. __   _______  __    ______     ___   .___________. __    ______   .__   __.    .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.
  //     /   \     /      | /      | /  __  \  |  |  |  | |  \ |  | |           |   |  \ |  |  /  __  \  |           ||  | |   ____||  |  /      |   /   \  |           ||  |  /  __  \  |  \ |  |    |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |
  //    /  ^  \   |  ,----'|  ,----'|  |  |  | |  |  |  | |   \|  | `---|  |----`   |   \|  | |  |  |  | `---|  |----`|  | |  |__   |  | |  ,----'  /  ^  \ `---|  |----`|  | |  |  |  | |   \|  |    |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`
  //   /  /_\  \  |  |     |  |     |  |  |  | |  |  |  | |  . `  |     |  |        |  . `  | |  |  |  |     |  |     |  | |   __|  |  | |  |      /  /_\  \    |  |     |  | |  |  |  | |  . `  |    |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |
  //  /  _____  \ |  `----.|  `----.|  `--'  | |  `--'  | |  |\   |     |  |        |  |\   | |  `--'  |     |  |     |  | |  |     |  | |  `----./  _____  \   |  |     |  | |  `--'  | |  |\   |    |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |
  // /__/     \__\ \______| \______| \______/   \______/  |__| \__|     |__|        |__| \__|  \______/      |__|     |__| |__|     |__|  \______/__/     \__\  |__|     |__|  \______/  |__| \__|    | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|
  private final AccountNotificationPostCommentValidRepository accountNotificationPostCommentValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public String createAccountNotificationPostComment(AccountNotificationPostComment entity) {
    return accountNotificationPostCommentValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAccountNotificationPostComment(String id) {
    return validateAuditorPhysical(accountNotificationPostCommentValidRepository.findById(id));
  }

  @Override
  public Optional<AccountNotificationPostComment> getAccountNotificationPostCommentOptional(String id) {
    return accountNotificationPostCommentValidRepository.findById(id);
  }

  @Override
  public AccountNotificationPostComment getAccountNotificationPostComment(String id) {
    return accountNotificationPostCommentValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
