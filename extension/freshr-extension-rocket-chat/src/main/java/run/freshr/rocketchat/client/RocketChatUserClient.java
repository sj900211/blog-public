package run.freshr.rocketchat.client;

import static run.freshr.rocketchat.configurations.RocketChatConfig.apiUsersCreate;
import static run.freshr.rocketchat.configurations.RocketChatConfig.apiUsersDelete;
import static run.freshr.rocketchat.configurations.RocketChatConfig.apiUsersLogin;
import static run.freshr.rocketchat.configurations.RocketChatConfig.apiUsersSetAvatar;
import static run.freshr.rocketchat.configurations.RocketChatConfig.apiUsersUpdate;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import run.freshr.rocketchat.dto.request.RocketChatCreateUserRequest;
import run.freshr.rocketchat.dto.request.RocketChatDeleteUserRequest;
import run.freshr.rocketchat.dto.request.RocketChatLoginUserRequest;
import run.freshr.rocketchat.dto.request.RocketChatSetAvatarRequest;
import run.freshr.rocketchat.dto.request.RocketChatUpdateUserRequest;
import run.freshr.rocketchat.dto.response.RocketChatCreateUserResponse;
import run.freshr.rocketchat.dto.response.RocketChatDeleteUserResponse;
import run.freshr.rocketchat.dto.response.RocketChatLoginUserResponse;
import run.freshr.rocketchat.dto.response.RocketChatSetAvatarResponse;
import run.freshr.rocketchat.dto.response.RocketChatUpdateUserResponse;

@FeignClient(name = "RocketChatAPIOpenFeignClient", url = "${freshr.rocket-chat.host}")
public interface RocketChatUserClient {

  @PostMapping(apiUsersLogin)
  ResponseEntity<RocketChatLoginUserResponse> login(
      @RequestBody @Valid RocketChatLoginUserRequest dto);

  @PostMapping(apiUsersCreate)
  ResponseEntity<RocketChatCreateUserResponse> createUser(
      @RequestHeader("X-Auth-Token") String authToken,
      @RequestHeader("X-User-Id") String userId,
      @RequestBody @Valid RocketChatCreateUserRequest dto);

  @PostMapping(apiUsersUpdate)
  ResponseEntity<RocketChatUpdateUserResponse> updateUser(
      @RequestHeader("X-Auth-Token") String authToken,
      @RequestHeader("X-User-Id") String userId,
      @RequestBody @Valid RocketChatUpdateUserRequest dto);

  @PostMapping(apiUsersSetAvatar)
  ResponseEntity<RocketChatSetAvatarResponse> setAvatar(
      @RequestHeader("X-Auth-Token") String authToken,
      @RequestHeader("X-User-Id") String userId,
      @RequestBody @Valid RocketChatSetAvatarRequest dto);

  @PostMapping(apiUsersDelete)
  ResponseEntity<RocketChatDeleteUserResponse> deleteUser(
      @RequestHeader("X-Auth-Token") String authToken,
      @RequestHeader("X-User-Id") String userId,
      @RequestBody @Valid RocketChatDeleteUserRequest dto);

}
