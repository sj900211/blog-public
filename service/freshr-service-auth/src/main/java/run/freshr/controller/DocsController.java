package run.freshr.controller;

import static run.freshr.common.configurations.URIConfig.uriDocsDepth1;
import static run.freshr.common.configurations.URIConfig.uriDocsDepth2;
import static run.freshr.common.configurations.URIConfig.uriDocsDepth3;
import static run.freshr.common.configurations.URIConfig.uriDocsIndex;
import static run.freshr.common.utils.RestUtil.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 문서 관리
 *
 * @author FreshR
 * @apiNote API 문서 View 클래스를 반환하는 기능들이 정의되어 있음
 * @since 2023. 1. 13. 오전 10:52:24
 */
@Slf4j
@RestController
public class DocsController {

  private final String DOCS = "docs";

  @GetMapping(uriDocsIndex)
  public ModelAndView viewDocs(ModelAndView mav) {
    log.info("DocsController.viewDocs");

    mav.setView(new RedirectView("/" + DOCS + "/index", true));

    return mav;
  }

  @GetMapping(uriDocsDepth1)
  public ModelAndView viewDocs(@PathVariable String depth1, ModelAndView mav) {
    log.info("DocsController.viewDocs");

    return view(mav, DOCS, depth1);
  }

  @GetMapping(uriDocsDepth2)
  public ModelAndView viewDocs(@PathVariable String depth1, @PathVariable String depth2,
      ModelAndView mav) {
    log.info("DocsController.viewDocs");

    return view(mav, DOCS, depth1, depth2);
  }

  @GetMapping(uriDocsDepth3)
  public ModelAndView viewDocs(@PathVariable String depth1, @PathVariable String depth2,
      @PathVariable String depth3, ModelAndView mav) {
    log.info("DocsController.viewDocs");

    return view(mav, DOCS, depth1, depth2, depth3);
  }

}
