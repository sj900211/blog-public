package run.freshr.controller;

import static run.freshr.common.configurations.URIConfig.uriCommonEnum;
import static run.freshr.common.configurations.URIConfig.uriCommonEnumPick;
import static run.freshr.common.configurations.URIConfig.uriCommonHeartbeat;
import static run.freshr.common.utils.RestUtil.getConfig;
import static run.freshr.common.utils.RestUtil.ok;
import static run.freshr.domain.auth.enumerations.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.USER;
import static java.lang.System.lineSeparator;
import static java.nio.file.Files.readAllLines;
import static java.util.stream.Collectors.joining;

import run.freshr.common.mappers.EnumMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공통 관리 controller
 *
 * @author 류성재
 * @apiNote 공통 관리 controller
 * @since 2023. 6. 16. 오후 4:20:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CommonController {

  private final EnumMapper enumMapper;

  /**
   * Health check
   *
   * @return heart beat
   * @throws IOException io exception
   * @apiNote Health check
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:20:33
   */
  @GetMapping(uriCommonHeartbeat)
  public String getHeartBeat() throws IOException {
    log.info("CommonController.getHeartBeat");

    return readAllLines(getConfig().getHeartbeat().getFile().toPath())
        .stream()
        .collect(joining(lineSeparator()));
  }

  //  _______ .__   __.  __    __  .___  ___.
  // |   ____||  \ |  | |  |  |  | |   \/   |
  // |  |__   |   \|  | |  |  |  | |  \  /  |
  // |   __|  |  . `  | |  |  |  | |  |\/|  |
  // |  |____ |  |\   | |  `--'  | |  |  |  |
  // |_______||__| \__|  \______/  |__|  |__|

  /**
   * 열거형 Data 조회 - All
   *
   * @return enum list
   * @apiNote 열거형 Data 조회 - All
   * @author 류성재
   * @since 2023. 6. 20. 오전 9:06:20
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriCommonEnum)
  public ResponseEntity<?> getEnumList() {
    log.info("CommonController.getEnumList");

    return ok(enumMapper.getAll());
  }

  /**
   * 열거형 Data 조회 - One To Many
   *
   * @param pick KEY 값
   * @return enum
   * @apiNote 열거형 Data 조회 - One To Many
   * @author 류성재
   * @since 2023. 6. 20. 오전 9:06:27
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriCommonEnumPick)
  public ResponseEntity<?> getEnum(@PathVariable String pick) {
    log.info("CommonController.getEnum");

    return ok(enumMapper.get(pick.toLowerCase()));
  }

}
