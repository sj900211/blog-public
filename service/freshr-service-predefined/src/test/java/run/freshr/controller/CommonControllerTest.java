package run.freshr.controller;

import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static run.freshr.common.configurations.URIConfig.uriCommonEnum;
import static run.freshr.common.configurations.URIConfig.uriCommonEnumPick;
import static com.google.common.base.CaseFormat.LOWER_HYPHEN;
import static com.google.common.base.CaseFormat.UPPER_CAMEL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import run.freshr.common.annotations.Docs;
import run.freshr.common.annotations.DocsGroup;
import run.freshr.common.enumerations.Gender;
import run.freshr.common.extensions.TestExtension;
import run.freshr.domain.common.EnumDocs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
@DisplayName("공통 관리")
@DocsGroup(name = "common")
class CommonControllerTest extends TestExtension {

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  @Test
  @DisplayName("열거형 Data 조회 - All")
  @Docs
  public void getEnumList() throws Exception {
    log.info("CommonControllerTest.getEnumList");

    setAnonymous();

    GET(uriCommonEnum)
        .andDo(print())
        .andDo(docsPopup(EnumDocs.Response.getEnumList(service.getEnumAll())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("열거형 Data 조회 - One To Many")
  @Docs(existsPathParameters = true)
  public void getEnum() throws Exception {
    log.info("CommonControllerTest.getEnum");

    setAnonymous();

    GET(uriCommonEnumPick,
        UPPER_CAMEL.to(LOWER_HYPHEN, Gender.class.getSimpleName()).toLowerCase())
        .andDo(print())
        .andDo(docs(pathParameters(EnumDocs.Request.getEnum())))
        .andExpect(status().isOk());
  }

}