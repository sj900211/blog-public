package run.freshr.domain.auth;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.account.entity.QAccount.account;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import run.freshr.common.docs.DataDocs;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;

@Slf4j
public class SignDocs {

  public static class Request {
    public static List<FieldDescriptor> signIn() {
      log.info("SignDocs.Request.signIn");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("계정")

          .field(account.username, "고유 아이디 [RSA 암호화]")
          .field(account.password, "비밀번호 [RSA 암호화]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> changePassword() {
      log.info("SignDocs.Request.changePassword");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("계정")

          .field("originPassword", "기존 비밀번호 [RSA 암호화]", STRING)
          .field(account.password, "비밀번호 [RSA 암호화]")

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> refreshToken() {
      log.info("SignDocs.Request.refreshToken");

      return PrintUtil
          .builder()
          .field("accessToken", "Access 토큰", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> updateInfo() {
      log.info("AccountDocs.Request.updateInfo");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("계정")
          .field(account.nickname, "닉네임 [RSA 암호화]")
          .field(account.introduce, account.gender, account.birth)

          .prefixOptional()

          .prefixDescription("계정 프로필 이미지")
          .field(account.profile, "정보", OBJECT)
          .field(account.profile.id)

          .prefixDescription("해시태그")
          .field(account.hashtagList, "목록", ARRAY)
          .field(account.hashtagList.any().hashtag, "정보", OBJECT)
          .field(account.hashtagList.any().hashtag.id)

          .build()
          .getFieldList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> signIn() {
      log.info("SignDocs.Response.signIn");

      return ResponseDocs
          .data()

          .field("accessToken", "접속 토큰", STRING)
          .field("refreshToken", "갱신 토큰", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> refreshToken() {
      log.info("SignDocs.Response.refreshToken");

      return ResponseDocs
          .data()

          .field("accessToken", "접속 토큰", STRING)

          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getInfo() {
      log.info("AccountDocs.Response.getInfo");

      return ResponseDocs
          .data()

          .prefixDescription("계정")
          .field(account.id, account.uuid, account.username, account.nickname,
              account.introduce, account.gender, account.birth,
              account.createAt, account.updateAt)

          .prefixOptional()
          .field(account.signAt)

          .prefixDescription("계정 프로필 이미지")
          .field(account.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData(
              "data.profile", "계정 프로필 이미지"))

          .prefixDescription("해시태그")
          .field(account.hashtagList, "목록", ARRAY)
          .field(account.hashtagList.any().hashtag, "정보", OBJECT)
          .field(account.hashtagList.any().hashtag.id)

          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
