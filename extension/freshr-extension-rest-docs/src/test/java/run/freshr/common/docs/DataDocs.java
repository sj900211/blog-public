package run.freshr.common.docs;

import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.account.entity.QAccount.account;
import static run.freshr.domain.predefined.entity.QAttach.attach;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.utils.PrintUtil;
import run.freshr.common.utils.PrintUtil.Builder;

@Slf4j
public class DataDocs {

  private static Builder settings(String prefix, String description, Boolean optional) {
    Builder builder = PrintUtil.builder();

    if (hasLength(prefix)) {
      builder.prefix(prefix);
    }

    if (hasLength(description)) {
      builder.prefixDescription(prefix);
    }

    builder.prefixOptional(optional);

    return builder;
  }

  public static class Account {
    public static List<FieldDescriptor> auditorLogicalData(String prefix, String description) {
      return auditorLogicalData(prefix, description, false);
    }

    public static List<FieldDescriptor> auditorLogicalData(String prefix) {
      return auditorLogicalData(prefix, "데이터", false);
    }

    public static List<FieldDescriptor> auditorLogicalData(String prefix, String description,
        Boolean optional) {
      return PrintUtil.builder()
          .addField(auditorData(prefix + "creator",
              description + " 작성자", optional))
          .addField(auditorData(prefix + "updater",
              description + " 마지막 수정자", optional))
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> auditorPhysicalData(String prefix) {
      return auditorPhysicalData(prefix + "creator", "작성자", false);
    }

    public static List<FieldDescriptor> auditorPhysicalData(String prefix, String description) {
      return auditorPhysicalData(prefix + "creator",
          description + " 작성자", false);
    }

    public static List<FieldDescriptor> auditorPhysicalData(String prefix, String description,
        Boolean optional) {
      return auditorData(prefix + "creator", description + " 작성자", optional);
    }

    public static List<FieldDescriptor> auditorData(String prefix) {
      return auditorData(prefix, "계정", true);
    }

    public static List<FieldDescriptor> auditorData(String prefix, String description) {
      return auditorData(prefix, description, true);
    }

    public static List<FieldDescriptor> auditorData(String prefix, String description,
        Boolean optional) {
      log.info("DataDocs.Account.auditorData");

      return settings(prefix, description, optional)
          .field(account.id)
          .linkField(account.username)

          .prefixOptional()
          .field(account.nickname)

          .field(account.profile, "프로필 이미지 정보", OBJECT)
          .addField(Predefined.attachData(prefix + ".profile",
              description + " 프로필 이미지", true))

          .build()
          .getFieldList();
    }
  }

  public static class Predefined {
    public static List<FieldDescriptor> attachData(String prefix) {
      return attachData(prefix, "파일", true);
    }

    public static List<FieldDescriptor> attachData(String prefix, String description) {
      return attachData(prefix, description, true);
    }

    public static List<FieldDescriptor> attachData(String prefix, String description,
        Boolean optional) {
      log.info("DataDocs.Predefined.attachData");

      return settings(prefix, description, optional)
          .field(attach.id, attach.contentType, attach.filename, attach.size)
          .field("url", "리소스 URL", STRING)
          .prefixOptional()
          .field(attach.alt, attach.title)
          .build()
          .getFieldList();
    }
  }

}
