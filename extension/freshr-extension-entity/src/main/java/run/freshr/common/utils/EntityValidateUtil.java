package run.freshr.common.utils;

import run.freshr.common.extensions.entity.EntityAuditLogicalExtension;
import run.freshr.common.extensions.entity.EntityAuditPhysicalExtension;
import run.freshr.common.extensions.entity.EntityLogicalExtension;
import run.freshr.common.extensions.entity.EntityPhysicalExtension;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EntityValidateUtil {

  public static <E extends EntityAuditLogicalExtension<?>> boolean validateAuditorLogical(
      Optional<E> optional) {
    log.info("EntityValidateUtil.validateAuditorLogical");

    if (optional.isEmpty()) {
      return false;
    }

    E entity = optional.get();

    return !entity.getDeleteFlag() && entity.getUseFlag();
  }

  public static <E extends EntityLogicalExtension> boolean validateLogical(Optional<E> optional) {
    log.info("EntityValidateUtil.validateLogical");

    if (optional.isEmpty()) {
      return false;
    }

    E entity = optional.get();

    return !entity.getDeleteFlag() && entity.getUseFlag();
  }

  public static <E extends EntityPhysicalExtension> boolean validatePhysical(Optional<E> optional) {
    log.info("EntityValidateUtil.validateLogical");

    return optional.isPresent();
  }

  public static <E extends EntityAuditPhysicalExtension<?>> boolean validateAuditorPhysical(
      Optional<E> optional) {
    log.info("EntityValidateUtil.validateAuditorPhysical");

    return optional.isPresent();
  }

}
