package run.freshr.service;

import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.UTC;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.utils.MapperUtil.map;
import static run.freshr.common.utils.RestUtilAuthAware.getSignedId;
import static run.freshr.common.utils.RestUtilAware.checkProfile;
import static run.freshr.common.utils.RestUtilAware.error;
import static run.freshr.common.utils.RestUtilAware.getExceptions;
import static run.freshr.common.utils.RestUtilAware.ok;
import static run.freshr.common.utils.StringUtil.padding;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import run.freshr.common.data.CursorData;
import run.freshr.common.data.IdDocumentData;
import run.freshr.common.data.PutData;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.dto.response.IdResponse;
import run.freshr.common.kafka.KafkaData;
import run.freshr.common.kafka.KafkaEvent;
import run.freshr.common.kafka.KafkaProducer;
import run.freshr.common.kafka.KafkaTopic;
import run.freshr.common.unit.MinioUnit;
import run.freshr.common.utils.RestUtilAware;
import run.freshr.common.utils.StringUtil;
import run.freshr.domain.logging.bus.LogBasicImageBus;
import run.freshr.domain.logging.elasticsearch.data.LogBasicImageData;
import run.freshr.domain.logging.enumerations.LogBasicImageType;
import run.freshr.domain.predefined.dto.request.AttachCreateRequest;
import run.freshr.domain.predefined.dto.request.BasicImageSaveRequest;
import run.freshr.domain.predefined.dto.response.AttachResponse;
import run.freshr.domain.predefined.dto.response.BasicImageResponse;
import run.freshr.domain.predefined.dto.response.StatisticHashtagResponse;
import run.freshr.domain.predefined.entity.Attach;
import run.freshr.domain.predefined.entity.Attach.AttachBuilder;
import run.freshr.domain.predefined.entity.BasicImage;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.predefined.unit.elasticsearch.StatisticHashtagUnit;
import run.freshr.domain.predefined.unit.jpa.AttachUnit;
import run.freshr.domain.predefined.unit.jpa.BasicImageUnit;
import run.freshr.domain.predefined.unit.jpa.CycleUnit;
import run.freshr.domain.predefined.unit.jpa.HashtagUnit;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;
import run.freshr.domain.statistic.elasticsearch.StatisticHashtag;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PredefinedServiceImpl implements PredefinedService {

  private final AttachUnit attachUnit;
  private final BasicImageUnit basicImageUnit;
  private final HashtagUnit hashtagUnit;
  private final CycleUnit cycleUnit;

  private final StatisticHashtagUnit statisticHashtagUnit;

  private final MinioUnit minioUnit;

  private final KafkaProducer producer;

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  @Override
  @Transactional
  public ResponseEntity<?> createAttach(AttachCreateRequest dto) throws ServerException,
      InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    log.info("PredefinedService.createAttach");

    List<MultipartFile> files = dto.getFiles();
    List<Long> idList = new ArrayList<>();
    String directory = ofNullable(dto.getDirectory()).orElse(".temp");
    String alt = dto.getAlt();
    String title = dto.getTitle();

    for (MultipartFile file : files) {
      String contentType = ofNullable(file.getContentType()).orElse("");
      String filename = ofNullable(file.getOriginalFilename()).orElse("");
      PutData putData = minioUnit.upload(directory, file);
      String key = putData.getPhysical();
      AttachBuilder entityBuilder = Attach.builder()
          .contentType(contentType)
          .filename(filename)
          .key(key)
          .size(file.getSize());

      if (hasLength(alt)) {
        entityBuilder.alt(alt);
      }

      if (hasLength(title)) {
        entityBuilder.title(title);
      }

      Long id = attachUnit.create(entityBuilder.build());

      idList.add(id);
    }

    List<IdResponse<Long>> list = idList.stream().map(RestUtilAware::buildId).toList();

    return ok(list);
  }

  @Override
  public ResponseEntity<?> getAttach(Long id) {
    log.info("PredefinedService.getAttach");

    if (!attachUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Attach entity = attachUnit.get(id);

    if (!(!entity.getDeleteFlag() && entity.getUseFlag())) {
      return error(getExceptions().getEntityNotFound());
    }

    return ok(map(entity, AttachResponse.class));
  }

  @Override
  public ResponseEntity<?> downloadAttach(Long id) throws ServerException,
      InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    log.info("PredefinedService.downloadAttach");

    if (!attachUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Attach entity = attachUnit.get(id);

    if (!(!entity.getDeleteFlag() && entity.getUseFlag())) {
      return error(getExceptions().getEntityNotFound());
    }

    if (checkProfile("test", "local")) {
      return ok();
    }

    return minioUnit.download(entity.getFilename(), entity.getKey());
  }

  @Override
  @Transactional
  public ResponseEntity<?> removeAttach(Long id) {
    log.info("PredefinedService.removeAttach");

    if (!attachUnit.exists(id)) {
      return error(getExceptions().getEntityNotFound());
    }

    Attach entity = attachUnit.get(id);

    if (!(!entity.getDeleteFlag() && entity.getUseFlag())) {
      return error(getExceptions().getEntityNotFound());
    }

    entity.removeEntity();

    return ok();
  }

  // .______        ___           _______. __    ______     __  .___  ___.      ___       _______  _______
  // |   _  \      /   \         /       ||  |  /      |   |  | |   \/   |     /   \     /  _____||   ____|
  // |  |_)  |    /  ^  \       |   (----`|  | |  ,----'   |  | |  \  /  |    /  ^  \   |  |  __  |  |__
  // |   _  <    /  /_\  \       \   \    |  | |  |        |  | |  |\/|  |   /  /_\  \  |  | |_ | |   __|
  // |  |_)  |  /  _____  \  .----)   |   |  | |  `----.   |  | |  |  |  |  /  _____  \ |  |__| | |  |____
  // |______/  /__/     \__\ |_______/    |__|  \______|   |__| |__|  |__| /__/     \__\ \______| |_______|
  @Override
  @Transactional
  public ResponseEntity<?> saveBasicImage(BasicImageType type, BasicImageSaveRequest dto) {
    log.info("PredefinedService.saveBasicImage");

    long now = now().toInstant(UTC).toEpochMilli();
    List<BasicImage> beforeList = basicImageUnit.getList(type);

    beforeList.forEach(entity -> producer.publish(KafkaTopic.LOGGING_CREATE,
          KafkaData
              .builder()
              .event(KafkaEvent.LOGGING_PREDEFINED_BASIC_IMAGE)
              .data(LogBasicImageBus
                  .builder()
                  .id(padding(cycleUnit.getCycleSequence(), 9) + now)
                  .type(LogBasicImageType.DELETE)
                  .before(LogBasicImageData
                      .builder()
                      .sort(entity.getSort())
                      .type(entity.getType())
                      .image(IdDocumentData
                          .<Long>builder()
                          .id(entity.getImage().getId())
                          .build())
                      .build())
                  .basicImage(IdDocumentData
                      .<Long>builder()
                      .id(entity.getId())
                      .build())
                  .scribe(IdDocumentData
                      .<Long>builder()
                      .id(getSignedId())
                      .build())
                  .build())
              .build()));

    basicImageUnit.deleteAll(type);

    List<BasicImage> entities = dto.getList()
        .stream()
        .map(item -> {
          Attach image = attachUnit.get(item.getImage().getId());

          image.changeIsPublic();

          return BasicImage
              .builder()
              .type(type)
              .sort(item.getSort())
              .image(image)
              .build();
        })
        .toList();

    basicImageUnit.create(entities);

    entities.forEach(entity -> producer.publish(KafkaTopic.LOGGING_CREATE,
        KafkaData
            .builder()
            .event(KafkaEvent.LOGGING_PREDEFINED_BASIC_IMAGE)
            .data(LogBasicImageBus
                .builder()
                .id(padding(cycleUnit.getCycleSequence(), 9) + now)
                .type(LogBasicImageType.CREATE)
                .after(LogBasicImageData
                    .builder()
                    .sort(entity.getSort())
                    .type(entity.getType())
                    .image(IdDocumentData
                        .<Long>builder()
                        .id(entity.getImage().getId())
                        .build())
                    .build())
                .basicImage(IdDocumentData
                    .<Long>builder()
                    .id(entity.getId())
                    .build())
                .scribe(IdDocumentData
                    .<Long>builder()
                    .id(getSignedId())
                    .build())
                .build())
            .build()));

    return ok();
  }

  @Override
  public ResponseEntity<?> getBasicImageList(BasicImageType type) {
    log.info("PredefinedService.getBasicImageList");

    List<BasicImage> entityList = basicImageUnit.getList(type);
    List<BasicImageResponse> list = map(entityList, BasicImageResponse.class);

    return ok(list);
  }

  @Override
  @Transactional
  public ResponseEntity<?> createHashtag(IdRequest<String> dto) {
    log.info("PredefinedService.createHashtag");

    String id = dto.getId();

    Boolean exists = hashtagUnit.exists(id);

    if (exists) {
      return error(getExceptions().getDuplicate());
    }

    Hashtag entity = Hashtag
        .builder()
        .id(id)
        .build();

    hashtagUnit.create(entity);

    StatisticHashtag document = StatisticHashtag
        .builder()
        .id(id)
        .accountCount(0L)
        .blogCount(0L)
        .postCount(0L)
        .build();

    statisticHashtagUnit.save(document);

    return ok();
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  @Override
  public ResponseEntity<?> getHashtagPage(PredefinedStringSearch search) {
    log.info("PredefinedService.getHashtagPage");

    Page<StatisticHashtag> documentPage = statisticHashtagUnit.getPage(search);
    Page<StatisticHashtagResponse> page = documentPage
        .map(entity -> map(entity, StatisticHashtagResponse.class));

    return ok(page);
  }

}
