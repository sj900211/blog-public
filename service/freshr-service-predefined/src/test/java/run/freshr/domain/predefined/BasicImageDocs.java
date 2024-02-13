package run.freshr.domain.predefined;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static run.freshr.domain.predefined.entity.QBasicImage.basicImage;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.DataDocs;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;

@Slf4j
public class BasicImageDocs {

  public static class Request {
    public static List<ParameterDescriptor> saveBasicImagePath() {
      log.info("BasicImageDocs.Request.saveBasicImagePath");

      return PrintUtil
          .builder()

          .prefixDescription("기본 이미지")

          .linkParameter("predefined-docs-save-basic-image-type", basicImage.type)

          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> saveBasicImage() {
      log.info("BasicImageDocs.Request.saveBasicImage");

      return PrintUtil
          .builder()

          .prefixDescription("기본 이미지")
          .field("list", "데이터 목록", ARRAY)

          .prefix("list[]")

          .field(basicImage.sort)
          .field(basicImage.image, "파일 데이터", OBJECT)
          .field(basicImage.image.id)

          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getBasicImageList() {
      log.info("BasicImageDocs.Request.getBasicImageList");

      return PrintUtil
          .builder()

          .prefixDescription("기본 이미지")

          .linkParameter("predefined-docs-save-basic-image-type", basicImage.type)

          .build()
          .getParameterList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> getBasicImageList() {
      log.info("BasicImageDocs.Response.getBasicImageList");

      return ResponseDocs
          .list()

          .prefixDescription("기본 이미지")

          .field(basicImage.id, basicImage.sort, basicImage.createAt, basicImage.updateAt)
          .field(basicImage.image, "파일 정보", OBJECT)

          .addField(DataDocs.Predefined.attachData("list[].image", "기본 이미지 파일"))
          .addField(DataDocs.Account.auditorLogicalData("list[]", "기본 이미지"))

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
