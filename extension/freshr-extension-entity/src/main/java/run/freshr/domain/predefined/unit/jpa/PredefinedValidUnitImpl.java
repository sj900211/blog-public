package run.freshr.domain.predefined.unit.jpa;

import static run.freshr.common.utils.EntityValidateUtil.validateAuditorLogical;
import static run.freshr.common.utils.EntityValidateUtil.validatePhysical;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.BasicImage;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.repository.jpa.AttachValidRepository;
import run.freshr.domain.predefined.repository.jpa.BasicImageValidRepository;
import run.freshr.domain.predefined.repository.jpa.HashtagValidRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PredefinedValidUnitImpl implements PredefinedValidUnit {

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  private final AttachValidRepository attachValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public long createAttach(Attach entity) {
    return attachValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateAttach(Long id) {
    log.info("PredefinedValidUnit.validateAttach");

    return validateAuditorLogical(attachValidRepository.findById(id));
  }

  @Override
  public boolean validateAttach(String key) {
    log.info("PredefinedValidUnit.validateAttach");

    return validateAuditorLogical(attachValidRepository.findByKey(key));
  }

  @Override
  public Optional<Attach> getAttachOptional(Long id) {
    log.info("PredefinedValidUnit.getAttachOptional");

    return attachValidRepository.findById(id);
  }

  @Override
  public Optional<Attach> getAttachOptional(String key) {
    log.info("PredefinedValidUnit.getAttachOptional");

    return attachValidRepository.findByKey(key);
  }

  @Override
  public Attach getAttach(Long id) {
    log.info("PredefinedValidUnit.Attach");

    return attachValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Attach getAttach(String key) {
    log.info("PredefinedValidUnit.Attach");

    return attachValidRepository.findByKey(key).orElseThrow(EntityNotFoundException::new);
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  private final HashtagValidRepository hashtagValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public String createHashtag(Hashtag entity) {
    return hashtagValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateHashtag(String id) {
    return validatePhysical(hashtagValidRepository.findById(id));
  }

  @Override
  public Optional<Hashtag> getHashtagOptional(String id) {
    return hashtagValidRepository.findById(id);
  }

  @Override
  public Hashtag getHashtag(String id) {
    return hashtagValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______        ___           _______. __    ______     __  .___  ___.      ___       _______  _______
  // |   _  \      /   \         /       ||  |  /      |   |  | |   \/   |     /   \     /  _____||   ____|
  // |  |_)  |    /  ^  \       |   (----`|  | |  ,----'   |  | |  \  /  |    /  ^  \   |  |  __  |  |__
  // |   _  <    /  /_\  \       \   \    |  | |  |        |  | |  |\/|  |   /  /_\  \  |  | |_ | |   __|
  // |  |_)  |  /  _____  \  .----)   |   |  | |  `----.   |  | |  |  |  |  /  _____  \ |  |__| | |  |____
  // |______/  /__/     \__\ |_______/    |__|  \______|   |__| |__|  |__| /__/     \__\ \______| |_______|
  private final BasicImageValidRepository basicImageValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public long createBasicImage(BasicImage entity) {
    return basicImageValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateBasicImage(Long id) {
    return validateAuditorLogical(basicImageValidRepository.findById(id));
  }

  @Override
  public Optional<BasicImage> getBasicImageOptional(Long id) {
    return basicImageValidRepository.findById(id);
  }

  @Override
  public BasicImage getBasicImage(Long id) {
    return basicImageValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
