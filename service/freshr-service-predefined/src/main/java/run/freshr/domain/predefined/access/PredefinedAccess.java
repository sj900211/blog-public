package run.freshr.domain.predefined.access;

import static run.freshr.common.utils.RestUtilAuthAware.checkManager;
import static run.freshr.common.utils.RestUtilAuthAware.getSignedId;

import run.freshr.domain.account.entity.Account;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.unit.jpa.AttachUnit;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PredefinedAccess {

  private final AttachUnit attachUnit;

  public boolean getAttach(Long id) {
    log.info("PredefinedAccess.getAttach");

    if (checkManager()) {
      return true;
    }

    Attach entity = attachUnit.get(id);

    if (entity.getIsPublic()) {
      return true;
    }

    Account owner = entity.getCreator();
    Long signedId = getSignedId();

    return Objects.equals(owner.getId(), signedId);
  }

  public boolean downloadAttach(Long id) {
    log.info("PredefinedAccess.downloadAttach");

    if (checkManager()) {
      return true;
    }

    Attach entity = attachUnit.get(id);

    if (entity.getIsPublic()) {
      return true;
    }

    Account owner = entity.getCreator();
    Long signedId = getSignedId();

    return Objects.equals(owner.getId(), signedId);
  }

  public boolean removeAttach(Long id) {
    log.info("PredefinedAccess.removeAttach");

    if (checkManager()) {
      return true;
    }

    Attach entity = attachUnit.get(id);

    Account owner = entity.getCreator();
    Long signedId = getSignedId();

    return Objects.equals(owner.getId(), signedId);
  }

}
