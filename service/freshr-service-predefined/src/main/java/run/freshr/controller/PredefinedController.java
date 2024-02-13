package run.freshr.controller;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static run.freshr.common.configurations.URIConfig.uriAttach;
import static run.freshr.common.configurations.URIConfig.uriAttachId;
import static run.freshr.common.configurations.URIConfig.uriAttachIdDownload;
import static run.freshr.common.configurations.URIConfig.uriBasicImageType;
import static run.freshr.common.configurations.URIConfig.uriHashtag;
import static run.freshr.common.utils.RestUtilAware.error;
import static run.freshr.common.utils.RestUtilAware.field;
import static run.freshr.common.utils.RestUtilAware.getExceptions;
import static run.freshr.domain.auth.enumerations.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.USER;
import static run.freshr.domain.predefined.entity.QBasicImage.basicImage;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.common.data.ExceptionsData.Exceptions;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.domain.predefined.dto.request.AttachCreateRequest;
import run.freshr.domain.predefined.dto.request.BasicImageSaveRequest;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.predefined.validator.PredefinedValidator;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;
import run.freshr.service.PredefinedService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PredefinedController {

  private final PredefinedService service;
  private final PredefinedValidator validator;

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PostMapping(value = uriAttach, consumes = MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createAttach(@ModelAttribute @Valid AttachCreateRequest dto)
      throws IOException, ServerException,
      InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    log.info("PredefinedController.createAttach");

    return service.createAttach(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@predefinedAccess.getAttach(#id)")
  @GetMapping(uriAttachId)
  public ResponseEntity<?> getAttach(@PathVariable Long id) {
    log.info("PredefinedController.getAttach");

    return service.getAttach(id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@predefinedAccess.downloadAttach(#id)")
  @GetMapping(uriAttachIdDownload)
  public ResponseEntity<?> downloadAttach(@PathVariable Long id) throws IOException,
      ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    log.info("PredefinedController.downloadAttach");

    return service.downloadAttach(id);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PreAuthorize("@predefinedAccess.removeAttach(#id)")
  @DeleteMapping(uriAttachId)
  public ResponseEntity<?> removeAttach(@PathVariable Long id) {
    log.info("PredefinedController.removeAttach");

    return service.removeAttach(id);
  }

  // .______        ___           _______. __    ______     __  .___  ___.      ___       _______  _______
  // |   _  \      /   \         /       ||  |  /      |   |  | |   \/   |     /   \     /  _____||   ____|
  // |  |_)  |    /  ^  \       |   (----`|  | |  ,----'   |  | |  \  /  |    /  ^  \   |  |  __  |  |__
  // |   _  <    /  /_\  \       \   \    |  | |  |        |  | |  |\/|  |   /  /_\  \  |  | |_ | |   __|
  // |  |_)  |  /  _____  \  .----)   |   |  | |  `----.   |  | |  |  |  |  /  _____  \ |  |__| | |  |____
  // |______/  /__/     \__\ |_______/    |__|  \______|   |__| |__|  |__| /__/     \__\ \______| |_______|
  @Secured({MANAGER_MAJOR, MANAGER_MINOR})
  @PostMapping(uriBasicImageType)
  public ResponseEntity<?> saveBasicImage(@PathVariable String type,
      @RequestBody @Valid BasicImageSaveRequest dto, BindingResult bindingResult) {
    log.info("PredefinedController.saveBasicImage");

    validator.saveBasicImage(type, bindingResult);

    if (bindingResult.hasErrors()) {
      return error(bindingResult);
    }

    return service.saveBasicImage(BasicImageType.find(type), dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriBasicImageType)
  public ResponseEntity<?> getBasicImageList(@PathVariable String type) {
    log.info("PredefinedController.getBasicImageList");

    boolean validate = validator.getBasicImageList(type);

    if (validate) {
      Exceptions exception = getExceptions().getParameter();

      return error(exception, exception.getMessage(), new String[]{field(basicImage.type)});
    }

    return service.getBasicImageList(BasicImageType.find(type));
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  @Secured({MANAGER_MAJOR, MANAGER_MINOR})
  @PostMapping(uriHashtag)
  public ResponseEntity<?> createHashtag(@RequestBody @Valid IdRequest<String> dto) {
    log.info("PredefinedController.createHashtag");

    return service.createHashtag(dto);
  }

  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriHashtag)
  public ResponseEntity<?> getHashtagPage(@ModelAttribute @Valid PredefinedStringSearch search,
      Errors errors) {
    log.info("PredefinedController.getHashtagPage");

    validator.getHashtagPage(search, errors);

    if (errors.hasErrors()) {
      return error(errors);
    }

    return service.getHashtagPage(search);
  }

}
