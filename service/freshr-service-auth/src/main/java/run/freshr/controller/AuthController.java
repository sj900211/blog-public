package run.freshr.controller;

import static run.freshr.common.configurations.URIConfig.uriAuthCrypto;
import static run.freshr.common.configurations.URIConfig.uriAuthInfo;
import static run.freshr.common.configurations.URIConfig.uriAuthPassword;
import static run.freshr.common.configurations.URIConfig.uriAuthRefresh;
import static run.freshr.common.configurations.URIConfig.uriAuthSignIn;
import static run.freshr.common.configurations.URIConfig.uriAuthSignOut;
import static run.freshr.domain.auth.enumerations.Role.Secured.ANONYMOUS;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MAJOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.MANAGER_MINOR;
import static run.freshr.domain.auth.enumerations.Role.Secured.USER;

import run.freshr.domain.auth.dto.request.EncryptRequest;
import run.freshr.domain.auth.dto.request.RefreshTokenRequest;
import run.freshr.domain.auth.dto.request.SignChangePasswordRequest;
import run.freshr.domain.auth.dto.request.SignInRequest;
import run.freshr.domain.auth.dto.request.SignUpdateRequest;
import run.freshr.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 권한 관리 controller
 *
 * @author 류성재
 * @apiNote 권한 관리 controller
 * @since 2023. 6. 16. 오후 4:21:24
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService service;

  /**
   * RSA 공개키 조회
   *
   * @return public key
   * @apiNote RSA 공개키 조회
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @GetMapping(uriAuthCrypto)
  public ResponseEntity<?> getPublicKey() {
    log.info("AuthController.getPublicKey");

    return service.getPublicKey();
  }

  /**
   * RSA 암호화 조회
   *
   * @param dto {@link EncryptRequest}
   * @return encrypt rsa
   * @apiNote RSA 암호화 조회<br>
   *          사용하지 않는 것을 권장<br>
   *          RSA 암호화를 할 수 없는 플랫폼일 때 사용
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER, ANONYMOUS})
  @PostMapping(uriAuthCrypto)
  public ResponseEntity<?> getEncryptRsa(@RequestBody @Valid EncryptRequest dto) {
    log.info("AuthController.getPublicKey");

    return service.getEncryptRsa(dto);
  }

  /**
   * 로그인
   *
   * @param dto {@link SignInRequest}
   * @return response entity
   * @apiNote 로그인
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured(ANONYMOUS)
  @PostMapping(uriAuthSignIn)
  public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest dto) {
    log.info("AuthController.signIn");

    return service.signIn(dto);
  }

  /**
   * 로그아웃
   *
   * @return response entity
   * @apiNote 로그아웃
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PostMapping(uriAuthSignOut)
  public ResponseEntity<?> signOut() {
    log.info("AuthController.signOut");

    return service.signOut();
  }

  /**
   * 내 정보 조회
   *
   * @return info
   * @apiNote 내 정보 조회
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @GetMapping(uriAuthInfo)
  public ResponseEntity<?> getInfo() {
    log.info("AuthController.getInfo");

    return service.getInfo();
  }

  /**
   * 비밀번호 변경
   *
   * @param dto {@link SignChangePasswordRequest}
   * @return response entity
   * @apiNote 비밀번호 변경
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PutMapping(uriAuthPassword)
  public ResponseEntity<?> changePassword(@RequestBody @Valid SignChangePasswordRequest dto) {
    log.info("AuthController.changePassword");

    return service.changePassword(dto);
  }

  /**
   * 내 정보 수정
   *
   * @param dto {@link SignUpdateRequest}
   * @return response entity
   * @apiNote 내 정보 수정
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @PutMapping(uriAuthInfo)
  public ResponseEntity<?> updateInfo(@RequestBody @Valid SignUpdateRequest dto) {
    log.info("AuthController.updateInfo");

    return service.updateInfo(dto);
  }

  /**
   * 탈퇴
   *
   * @return response entity
   * @apiNote 탈퇴
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @Secured({MANAGER_MAJOR, MANAGER_MINOR, USER})
  @DeleteMapping(uriAuthInfo)
  public ResponseEntity<?> withdrawal() {
    log.info("AuthController.withdrawal");

    return service.withdrawal();
  }

  /**
   * Access 토큰 갱신
   *
   * @param request 요청 정보
   * @param dto     {@link RefreshTokenRequest}
   * @return response entity
   * @apiNote Access 토큰 갱신
   * @author 류성재
   * @since 2023. 6. 16. 오후 4:21:24
   */
  @PostMapping(uriAuthRefresh)
  public ResponseEntity<?> refreshAccessToken(HttpServletRequest request,
      @RequestBody @Valid RefreshTokenRequest dto) {
    log.info("AuthController.refreshAccessToken");

    return service.refreshAccessToken(request, dto);
  }

}
