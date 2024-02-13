package run.freshr.domain.predefined;

import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static run.freshr.domain.predefined.entity.QHashtag.hashtag;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;
import run.freshr.domain.predefined.vo.BPredefinedStringSearch;

@Slf4j
public class HashtagDocs {

  public static class Request {
    public static List<FieldDescriptor> createHashtag() {
      log.info("HashtagDocs.Request.createHashtag");

      return PrintUtil
          .builder()

          .field(hashtag.id, "해시태그")

          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getHashtagPage() {
      log.info("HashtagDocs.Request.getHashtagPage");

      return PrintUtil
          .builder()

          .parameter(BPredefinedStringSearch.page, BPredefinedStringSearch.size)

          .prefixOptional()
          .parameter(BPredefinedStringSearch.word)

          .build()
          .getParameterList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> getHashtagPage() {
      log.info("HashtagDocs.Response.getHashtagPage");

      return ResponseDocs
          .page()

          .field(hashtag.id, "해시태그")

          .field("accountCount", "연결 계정 수", NUMBER)
          .field("blogCount", "연결 블로그 수", NUMBER)
          .field("postCount", "연결 포스팅 수", NUMBER)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
