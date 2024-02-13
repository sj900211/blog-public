package run.freshr.service;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.domain.predefined.dto.request.AttachCreateRequest;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import run.freshr.domain.predefined.dto.request.BasicImageSaveRequest;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;

public interface PredefinedService {

  ResponseEntity<?> createAttach(AttachCreateRequest dto) throws IOException, ServerException,
      InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

  ResponseEntity<?> getAttach(Long id);

  ResponseEntity<?> downloadAttach(Long id) throws IOException, ServerException,
      InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

  ResponseEntity<?> removeAttach(Long id);

  ResponseEntity<?> saveBasicImage(BasicImageType type, BasicImageSaveRequest dto);

  ResponseEntity<?> getBasicImageList(BasicImageType type);

  ResponseEntity<?> createHashtag(IdRequest<String> dto);

  ResponseEntity<?> getHashtagPage(PredefinedStringSearch search);

}
