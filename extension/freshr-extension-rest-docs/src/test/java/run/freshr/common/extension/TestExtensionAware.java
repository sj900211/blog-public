package run.freshr.common.extension;

import static java.util.Arrays.stream;
import static java.util.List.of;
import static java.util.Objects.isNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.common.security.TokenProvider.signedId;
import static run.freshr.common.security.TokenProvider.signedRole;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.snippet.Snippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import run.freshr.common.extensions.request.SearchExtension;
import run.freshr.common.security.TokenProvider;
import run.freshr.domain.auth.enumerations.Role;

/**
 * 공통 테스트 설정 및 기능을 정의
 *
 * @author 류성재
 * @apiNote 공통 테스트 설정 및 기능을 정의
 * @since 2023. 1. 13. 오전 11:02:06
 */
@Slf4j
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@TestInstance(PER_CLASS)
public abstract class TestExtensionAware {

  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  protected TokenProvider provider;
  @Autowired
  private EntityManager entityManager;
  private MockMvc mockMvc;
  private RestDocumentationResultHandler document;

  @Value("${freshr.service.name}")
  private String serviceName;

  private final String SCHEME = "https";
  private final String HOST = "gateway";
  private final String DOCS_PATH = "{class-name}/{method-name}";
  private final String POPUP_DOCS_PATH = DOCS_PATH + "/popup";

  @BeforeEach
  public void beforeEach(WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    log.info("TestExtension.beforeEach");

    this.document = document(
        DOCS_PATH, // 문서 경로 설정
        preprocessRequest( // Request 설정
            modifyUris()
                .scheme(SCHEME)
                .host(HOST)
                .removePort(), // 문서에 노출되는 도메인 설정
            prettyPrint() // 정리해서 출력
        ),
        preprocessResponse(prettyPrint()) // Response 설정. 정리해서 출력
    );

    this.mockMvc = MockMvcBuilders // MockMvc 공통 설정. 문서 출력 설정
        .webAppContextSetup(webApplicationContext)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(document)
        .build();
  }

  /**
   * 데이터 반영
   *
   * @apiNote 지금까지의 영속성 컨텍스트 내용을 DB 에 반영
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:06
   */
  protected void apply() {
    log.info("TestExtension.apply");

    entityManager.flush(); // 영속성 컨텍스트 내용을 데이터베이스에 반영
    entityManager.clear(); // 영속성 컨텍스트 초기화
  }

  /**
   * Request Header 설정
   *
   * @param mockHttpServletRequestBuilder 요청 정보 builder
   * @return header
   * @apiNote 기본적인 Request Header 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  private MockHttpServletRequestBuilder setHeader(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setHeader");

    return setContextPath(mockHttpServletRequestBuilder)
        .contentType(APPLICATION_JSON)
        .accept(APPLICATION_JSON);
  }

  /**
   * Request Header 설정
   *
   * @param mockHttpServletRequestBuilder 요청 정보 builder
   * @return multipart
   * @apiNote multipart/form-data 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  private MockHttpServletRequestBuilder setMultipart(
      MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
    log.info("TestExtension.setMultipart");

    return setContextPath(mockHttpServletRequestBuilder)
        .contentType(MULTIPART_FORM_DATA)
        .accept(APPLICATION_JSON);
  }

  /**
   * POST 통신
   *
   * @param uri           URI 정보
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions POST(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.POST");

    return mockMvc.perform(setHeader(post(setContextPathURI(uri), pathVariables)));
  }

  /**
   * POST 통신
   *
   * @param uri           URI 정보
   * @param token         설정할 토큰 정보
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Authorization 에 JWT 토큰을 따로 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions POST_TOKEN(String uri, String token, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_TOKEN");

    return mockMvc.perform(setHeader(post(setContextPathURI(uri), pathVariables))
            .header("Authorization", "Bearer " + token));
  }

  /**
   * POST 통신
   *
   * @param <T>           요청 body 데이터 유형
   * @param uri           URI 정보
   * @param content       요청 body 데이터
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Body 를 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public <T> ResultActions POST_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_BODY");

    return mockMvc.perform(setHeader(post(setContextPathURI(uri), pathVariables))
            .content(objectMapper.writeValueAsString(content)));
  }

  /**
   * POST 통신
   *
   * @param uri           URI 정보
   * @param token         설정할 토큰 정보
   * @param content       요청 body 데이터
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Authorization 에 JWT 토큰을 따로 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public <T> ResultActions POST_TOKEN_BODY(String uri, String token, T content,
      Object... pathVariables)
      throws Exception {
    log.info("TestExtension.POST_TOKEN_BODY");

    return mockMvc.perform(setHeader(post(setContextPathURI(uri), pathVariables))
            .header("Authorization", "Bearer " + token)
            .content(objectMapper.writeValueAsString(content)));
  }

  /**
   * POST 통신
   *
   * @param uri               URI 정보
   * @param directory         저장할 directory 이름
   * @param mockMultipartFile 파일 데이터
   * @param pathVariables     path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Multipart 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions POST_MULTIPART(String uri, String directory,
      MockMultipartFile mockMultipartFile, Object... pathVariables) throws Exception {
    log.info("TestExtension.POST_MULTIPART");

    MockMultipartHttpServletRequestBuilder file = multipart(setContextPathURI(uri), pathVariables)
        .file(mockMultipartFile);

    if (hasLength(directory)) {
      file.param("directory", directory);
    }

    return mockMvc.perform(setMultipart(file));
  }

  /**
   * GET 통신
   *
   * @param uri           URI 정보
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions GET(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.GET");

    return mockMvc.perform(setHeader(get(setContextPathURI(uri), pathVariables)));
  }

  /**
   * GET 통신
   *
   * @param <T>           요청 query string 데이터 유형
   * @param uri           URI 정보
   * @param search        요청 query string 데이터
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Parameter 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public <T extends SearchExtension> ResultActions GET_PARAM(String uri, T search,
      Object... pathVariables) throws Exception {
    log.info("TestExtension.GET_PARAM");

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
        get(setContextPathURI(uri), pathVariables);

    if (!isNull(search)) {
      stream(search.getClass().getDeclaredFields()).forEach(field -> {
        try {
          field.setAccessible(true);

          if (!isNull(field.get(search))) {
            if (!field.getType().equals(List.class)) {
              mockHttpServletRequestBuilder.param(field.getName(), field.get(search).toString());
            } else {
              String valueString = field.get(search).toString();

              valueString = valueString.substring(1, valueString.length() - 1);

              List<String> valueList = of(valueString.split(", "));
              int max = valueList.size();

              for (int i = 0; i < max; i++) {
                mockHttpServletRequestBuilder
                    .param(field.getName() + "[" + i + "]", valueList.get(i));
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });

      stream(search.getClass().getSuperclass().getDeclaredFields()).forEach(field -> {
        try {
          field.setAccessible(true);

          if (!isNull(field.get(search))) {
            if (!field.getType().equals(List.class)) {
              mockHttpServletRequestBuilder.param(field.getName(), field.get(search).toString());
            } else {
              String valueString = field.get(search).toString();

              valueString = valueString.substring(1, valueString.length() - 1);

              List<String> valueList = of(valueString.split(", "));
              int max = valueList.size();

              for (int i = 0; i < max; i++) {
                mockHttpServletRequestBuilder
                    .param(field.getName() + "[" + i + "]", valueList.get(i));
              }
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
    }

    return mockMvc.perform(setHeader(mockHttpServletRequestBuilder));
  }

  /**
   * PUT 통신
   *
   * @param uri           URI 정보
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions PUT(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.PUT");

    return mockMvc.perform(setHeader(put(setContextPathURI(uri), pathVariables)));
  }

  /**
   * PUT 통신
   *
   * @param <T>           요청 body 데이터 유형
   * @param uri           URI 정보
   * @param content       요청 body 데이터
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Request Body 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public <T> ResultActions PUT_BODY(String uri, T content, Object... pathVariables)
      throws Exception {
    log.info("TestExtension.PUT_BODY");

    return mockMvc.perform(setHeader(put(setContextPathURI(uri), pathVariables))
        .content(objectMapper.writeValueAsString(content)));
  }

  /**
   * DELETE 통신
   *
   * @param uri           URI 정보
   * @param pathVariables path parameter 값 목록
   * @return result actions
   * @throws Exception exception
   * @apiNote Path Parameter 외에 설정한 정보가 없는 통신
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  public ResultActions DELETE(String uri, Object... pathVariables) throws Exception {
    log.info("TestExtension.DELETE");

    return mockMvc.perform(setHeader(delete(setContextPathURI(uri), pathVariables)));
  }

  /**
   * Document 작성
   *
   * @param snippets 문서 구성 요소
   * @return rest documentation result handler
   * @apiNote 코드를 조금이라도 짧게 만들고 싶어서 만든 기능...⭐
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected RestDocumentationResultHandler docs(Snippet... snippets) {
    log.info("TestExtension.docs");

    return document.document(snippets);
  }

  /**
   * 팝업 문서 설정
   *
   * @param snippets 문서 구성 요소
   * @return rest documentation result handler
   * @apiNote 팝업 문서 설정
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected RestDocumentationResultHandler docsPopup(Snippet... snippets) {
    log.info("TestExtension.docsPopup");

    this.document = document(
        POPUP_DOCS_PATH, // 문서 경로 설정
        preprocessRequest( // Request 설정
            modifyUris()
                .scheme(SCHEME)
                .host(HOST), // 문서에 노출되는 도메인 설정
            prettyPrint() // 정리해서 출력
        ),
        preprocessResponse(prettyPrint()) // Response 설정. 정리해서 출력
    );

    return document.document(snippets);
  }

  /**
   * 계정 일련 번호 조회
   *
   * @return signed id
   * @apiNote 통신중인 계정의 일련 번호 조회
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected Long getSignedId() {
    log.info("TestExtension.getSignedId");

    return signedId.get();
  }

  /**
   * 권한 조회
   *
   * @return signed role
   * @apiNote 통신중인 계정의 권한 조회
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected Role getSignedRole() {
    log.info("TestExtension.getSignedRole");

    return signedRole.get();
  }

  /**
   * 인증 정보 제거
   *
   * @apiNote 통신중인 인증 정보 제거
   * @author 류성재
   * @since 2023. 1. 13. 오전 11:02:07
   */
  protected void removeSigned() {
    log.info("TestExtension.removeSigned");

    signedRole.remove();
    signedId.remove();
  }

  protected MockHttpServletRequestBuilder setContextPath(MockHttpServletRequestBuilder mockRequest) {
    if (hasLength(serviceName)) {
      mockRequest.contextPath("/" + serviceName);
    }

    return mockRequest;
  }

  protected String setContextPathURI(String uri) {
    if (hasLength(serviceName)) {
      uri = "/" + serviceName + uri;
    }

    return uri;
  }

}
