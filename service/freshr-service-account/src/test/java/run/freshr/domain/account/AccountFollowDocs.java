package run.freshr.domain.account;

import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static run.freshr.domain.account.entity.QAccount.account;
import static run.freshr.domain.account.entity.mapping.QAccountFollow.accountFollow;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.DataDocs;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;

@Slf4j
public class AccountFollowDocs {

  public static class Request {
    public static List<ParameterDescriptor> existsFollow() {
      log.info("AccountFollowDocs.existsFollow");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .parameter(account.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> toggleFollow() {
      log.info("AccountFollowDocs.toggleFollow");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .parameter(account.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> getFollowingList() {
      log.info("AccountFollowDocs.getFollowingList");

      return ResponseDocs
          .list()

          .field(accountFollow.following, "팔로잉 사용자 정보", OBJECT)
          .field(accountFollow.following.id, accountFollow.following.uuid,
              accountFollow.following.username, accountFollow.following.nickname,
              accountFollow.following.birth)

          .linkField("account-gender", accountFollow.following.gender)

          .prefixDescription("팔로잉 사용자 프로필 이미지")
          .field(accountFollow.following.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("list[].following.profile",
              "팔로잉 사용자 프로필 이미지", true))

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getFollowerList() {
      log.info("AccountFollowDocs.getFollowerList");

      return ResponseDocs
          .list()

          .field(accountFollow.follower, "팔로워 사용자 정보", OBJECT)
          .field(accountFollow.follower.id, accountFollow.follower.uuid,
              accountFollow.follower.username, accountFollow.follower.nickname,
              accountFollow.follower.birth)

          .linkField("account-gender", accountFollow.follower.gender)

          .prefixDescription("팔로워 사용자 프로필 이미지")
          .field(accountFollow.follower.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("list[].follower.profile",
              "팔로워 사용자 프로필 이미지", true))

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> existsFollow() {
      log.info("AccountFollowDocs.existsFollow");

      return ResponseDocs
          .data()

          .field("is", "팔로우 여부", BOOLEAN)

          .clear()
          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
