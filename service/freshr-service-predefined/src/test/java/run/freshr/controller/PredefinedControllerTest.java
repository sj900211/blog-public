package run.freshr.controller;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.formParameters;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static run.freshr.TestRunner.attachIdList;
import static run.freshr.common.configurations.URIConfig.uriAttach;
import static run.freshr.common.configurations.URIConfig.uriAttachId;
import static run.freshr.common.configurations.URIConfig.uriAttachIdDownload;
import static run.freshr.common.configurations.URIConfig.uriBasicImageType;
import static run.freshr.common.configurations.URIConfig.uriHashtag;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import run.freshr.common.annotations.Docs;
import run.freshr.common.annotations.DocsGroup;
import run.freshr.common.annotations.DocsPopup;
import run.freshr.common.dto.request.IdRequest;
import run.freshr.common.extensions.TestExtension;
import run.freshr.domain.predefined.AttachDocs;
import run.freshr.domain.predefined.BasicImageDocs;
import run.freshr.domain.predefined.HashtagDocs;
import run.freshr.domain.predefined.dto.request.BasicImageSaveItem;
import run.freshr.domain.predefined.dto.request.BasicImageSaveRequest;
import run.freshr.domain.predefined.enumerations.BasicImageType;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;

@Slf4j
@DisplayName("사전 정의 관리")
@DocsGroup(name = "predefined")
public class PredefinedControllerTest extends TestExtension {

  //      ___   .___________.___________.    ___       ______  __    __
  //     /   \  |           |           |   /   \     /      ||  |  |  |
  //    /  ^  \ `---|  |----`---|  |----`  /  ^  \   |  ,----'|  |__|  |
  //   /  /_\  \    |  |        |  |      /  /_\  \  |  |     |   __   |
  //  /  _____  \   |  |        |  |     /  _____  \ |  `----.|  |  |  |
  // /__/     \__\  |__|        |__|    /__/     \__\ \______||__|  |__|
  @Test
  @DisplayName("파일 업로드")
  @Docs(existsRequestParts = true, existsFormParameters = true, existsResponseFields = true)
  public void createAttach() throws Exception {
    log.info("PredefinedControllerTest.createAttach");

    setSignedManagerMajor();

    apply();

    POST_MULTIPART(
        uriAttach,
        "temp",
        new MockMultipartFile("files", "original", IMAGE_PNG_VALUE,
            new byte[] {1})
    ).andDo(print())
        .andDo(docs(
            requestParts(AttachDocs.Request.createAttachFile()),
            formParameters(AttachDocs.Request.createAttach()),
            responseFields(AttachDocs.Response.createAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 조회")
  @Docs(existsPathParameters = true, existsResponseFields = true)
  public void getAttach() throws Exception {
    log.info("PredefinedControllerTest.getAttach");

    setSignedManagerMajor();

    apply();

    GET(uriAttachId, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(
            pathParameters(AttachDocs.Request.getAttach()),
            responseFields(AttachDocs.Response.getAttach())
        ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 다운로드")
  @Docs(existsPathParameters = true)
  public void getAttachDownload() throws Exception {
    log.info("PredefinedControllerTest.getAttachDownload");

    setSignedManagerMajor();

    apply();

    GET(uriAttachIdDownload, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.getAttach())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("파일 삭제")
  @Docs(existsPathParameters = true)
  public void removeAttach() throws Exception {
    log.info("PredefinedControllerTest.removeAttach");

    setSignedManagerMajor();

    apply();

    DELETE(uriAttachId, attachIdList.get(0))
        .andDo(print())
        .andDo(docs(pathParameters(AttachDocs.Request.removeAttach())))
        .andExpect(status().isOk());
  }

  // .______        ___           _______. __    ______     __  .___  ___.      ___       _______  _______
  // |   _  \      /   \         /       ||  |  /      |   |  | |   \/   |     /   \     /  _____||   ____|
  // |  |_)  |    /  ^  \       |   (----`|  | |  ,----'   |  | |  \  /  |    /  ^  \   |  |  __  |  |__
  // |   _  <    /  /_\  \       \   \    |  | |  |        |  | |  |\/|  |   /  /_\  \  |  | |_ | |   __|
  // |  |_)  |  /  _____  \  .----)   |   |  | |  `----.   |  | |  |  |  |  /  _____  \ |  |__| | |  |____
  // |______/  /__/     \__\ |_______/    |__|  \______|   |__| |__|  |__| /__/     \__\ \______| |_______|
  @Test
  @DisplayName("기본 이미지 저장")
  @Docs(existsPathParameters = true, existsRequestFields = true,
      popup = @DocsPopup(name = "predefined-docs-save-basic-image-type", include =
          "common-controller-test/get-enum-list/popup/popup-fields-basic-image-type.adoc"))
  public void saveBasicImage() throws Exception {
    log.info("PredefinedControllerTest.saveBasicImage");

    setSignedManagerMajor();

    apply();

    POST_BODY(
        uriBasicImageType,
        BasicImageSaveRequest
            .builder()
            .list(IntStream.range(1, 10).boxed()
                .map(index -> BasicImageSaveItem
                    .builder()
                    .sort(index - 1)
                    .image(IdRequest.<Long>builder().id(attachIdList.get(index)).build())
                    .build())
                .toList())
            .build(),
        BasicImageType.PROFILE.name().toLowerCase()
    ).andDo(print())
        .andDo(docs(
            pathParameters(BasicImageDocs.Request.saveBasicImagePath()),
            requestFields(BasicImageDocs.Request.saveBasicImage())
        )).andExpect(status().isOk());
  }

  @Test
  @DisplayName("기본 이미지 조회 - List")
  @Docs(existsPathParameters = true, existsResponseFields = true)
  public void getBasicImageList() throws Exception {
    log.info("PredefinedControllerTest.getBasicImageList");

    setSignedManagerMajor();

    apply();

    GET(uriBasicImageType, BasicImageType.PROFILE.name().toLowerCase())
        .andDo(print())
        .andDo(docs(
            pathParameters(BasicImageDocs.Request.getBasicImageList()),
            responseFields(BasicImageDocs.Response.getBasicImageList())
        )).andExpect(status().isOk());
  }

  //  __    __       ___           _______. __    __  .___________.    ___       _______
  // |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  @Test
  @DisplayName("해시태그 등록")
  @Docs(existsRequestFields = true)
  public void createHashtag() throws Exception {
    log.info("PredefinedControllerTest.createHashtag");

    setSignedManagerMajor();

    apply();

    POST_BODY(
        uriHashtag,
        IdRequest
            .<String>builder()
            .id("new_hashtag")
            .build()
    ).andDo(print())
        .andDo(docs(requestFields(HashtagDocs.Request.createHashtag())))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("해시태그 조회 - Page")
  @Docs(existsQueryParameters = true, existsResponseFields = true)
  public void getHashtagPage() throws Exception {
    log.info("PredefinedControllerTest.getHashtagPage");

    setSignedManagerMajor();

    apply();

    PredefinedStringSearch search = new PredefinedStringSearch();

    search.setPage(1);
    search.setSize(10);
    search.setWord("tag");

    GET_PARAM(uriHashtag, search)
        .andDo(print())
        .andDo(docs(
            queryParameters(HashtagDocs.Request.getHashtagPage()),
            responseFields(HashtagDocs.Response.getHashtagPage())
        )).andExpect(status().isOk());
  }

}
