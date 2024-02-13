package run.freshr.common.serializer;

import static run.freshr.common.enumerations.StatusEnum.PARAMETER;
import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;

import run.freshr.common.enumerations.StatusEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

/**
 * 에러 Json 변환
 *
 * @author 류성재
 * @apiNote api note
 * @since 2022. 12. 23. 오후 4:23:59
 */
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

  /**
   * 에러 변환 로직
   *
   * @param errors             에러
   * @param generator          JSON 제너레이터
   * @param serializerProvider 변환 프로바이더
   * @throws IOException the io exception
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  @Override
  public void serialize(Errors errors, JsonGenerator generator,
      SerializerProvider serializerProvider) throws IOException {
    generator.writeStartObject();

    StatusEnum parameter = PARAMETER;

    generator.writeStringField("name",
        UPPER_UNDERSCORE.to(LOWER_HYPHEN, parameter.getKey()));
    generator.writeStringField("code", parameter.getCode());
    generator.writeStringField("message", "Parameter Exception");

    generator.writeArrayFieldStart("list");

    errors.getFieldErrors().forEach(e -> {
      try {
        generator.writeStartObject();
        generator.writeStringField("field", e.getField());
        generator.writeStringField("objectName", e.getObjectName());
        generator.writeStringField("code", e.getCode());
        generator.writeStringField("defaultMessage", e.getDefaultMessage());
        generator.writeEndObject();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });

    errors.getGlobalErrors().forEach(e -> {
      try {
        generator.writeStartObject();
        generator.writeStringField("objectName", e.getObjectName());
        generator.writeStringField("code", e.getCode());
        generator.writeStringField("defaultMessage", e.getDefaultMessage());
        generator.writeEndObject();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });

    generator.writeEndArray();
  }

}
