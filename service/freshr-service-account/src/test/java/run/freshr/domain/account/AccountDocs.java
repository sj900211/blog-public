package run.freshr.domain.account;

import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.OBJECT;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static run.freshr.domain.account.entity.QAccount.account;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import run.freshr.common.docs.DataDocs;
import run.freshr.common.docs.ResponseDocs;
import run.freshr.common.utils.PrintUtil;
import run.freshr.domain.account.vo.BAccountSearch;

@Slf4j
public class AccountDocs {

  public static class Request {
    public static List<ParameterDescriptor> existsAccountByUsername() {
      log.info("AccountDocs.existsAccountByUsername");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.word, "검색어")

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> existsAccountByNickname() {
      log.info("AccountDocs.existsAccountByNickname");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.word, "검색어")

          .clear()
          .build()
          .getParameterList();
    }

    public static List<FieldDescriptor> join() {
      log.info("AccountDocs.join");

      return PrintUtil
          .builder()

          .field("rsa", "RSA 공개키", STRING)

          .prefixDescription("사용자")

          .field(account.username, "고유 아이디 - 이메일 [RSA 암호화]")
          .field(account.password, "비밀번호 [RSA 암호화]")
          .field(account.nickname, "닉네임 [RSA 암호화]")

          .field(account.gender, account.birth)

          .prefixOptional()
          .field(account.introduce)

          .prefixDescription("사용자 프로필 이미지")
          .field(account.profile, "정보", OBJECT)
          .field(account.profile.id)

          .prefixDescription("해시태그")
          .field(account.hashtagList, "목록", ARRAY)
          .field(account.hashtagList.any().hashtag, "정보", OBJECT)
          .field(account.hashtagList.any().hashtag.id)

          .build()
          .getFieldList();
    }

    public static List<ParameterDescriptor> getAccountPage() {
      log.info("AccountDocs.getAccountPage");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.page, BAccountSearch.size)

          .prefixOptional()
          .linkParameter("account-search-key", BAccountSearch.key)

          .prefixDescription("사용자")

          .linkParameter("account-gender", BAccountSearch.gender)
          .parameter(BAccountSearch.word,
              BAccountSearch.birthStartDate, BAccountSearch.birthEndDate)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAccountPageByManager() {
      log.info("AccountDocs.getAccountPageByManager");

      return PrintUtil
          .builder()

          .parameter(BAccountSearch.page, BAccountSearch.size)

          .prefixOptional()
          .linkParameter("account-search-key", BAccountSearch.key)

          .prefixDescription("사용자")

          .linkParameter("account-status", BAccountSearch.status)
          .linkParameter("account-gender", BAccountSearch.gender)
          .parameter(BAccountSearch.word,
              BAccountSearch.birthStartDate, BAccountSearch.birthEndDate)

          .clear()
          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAccount() {
      log.info("AccountDocs.getAccount");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .parameter(account.id)

          .build()
          .getParameterList();
    }

    public static List<ParameterDescriptor> getAccountByManager() {
      log.info("AccountDocs.getAccountByManager");

      return PrintUtil
          .builder()

          .prefixDescription("사용자")

          .parameter(account.id)

          .build()
          .getParameterList();
    }
  }

  public static class Response {
    public static List<FieldDescriptor> existsAccountByUsername() {
      log.info("AccountDocs.existsAccountByUsername");

      return ResponseDocs
          .data()

          .field("is", "중복 여부", BOOLEAN)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> existsAccountByNickname() {
      log.info("AccountDocs.existsAccountByNickname");

      return ResponseDocs
          .data()

          .field("is", "중복 여부", BOOLEAN)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> join() {
      log.info("AccountDocs.join");

      return ResponseDocs
          .data()

          .prefixDescription("사용자")

          .field(account.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccountPage() {
      log.info("AccountDocs.getAccountPage");

      return ResponseDocs
          .cursor()

          .prefixDescription("사용자")

          .field(account.id, account.uuid, account.username, account.nickname,
              account.birth, account.createAt, account.updateAt)

          .linkField("account-gender", account.gender)

          .prefixOptional()

          .field(account.signAt)

          .prefixDescription("사용자 프로필 이미지")
          .field(account.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("page.content[].profile",
              "사용자 프로필 이미지", true))

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccountPageByManager() {
      log.info("AccountDocs.getAccountPageByManager");

      return ResponseDocs
          .cursor()

          .prefixDescription("사용자")

          .field(account.id, account.uuid, account.username, account.nickname,
              account.birth, account.createAt, account.updateAt)

          .linkField("account-status", account.status)
          .linkField("account-privilege", account.privilege)
          .linkField("account-gender", account.gender)

          .prefixOptional()

          .field(account.signAt)

          .prefixDescription("사용자 프로필 이미지")
          .field(account.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("page.content[].profile",
              "사용자 프로필 이미지", true))

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccount() {
      log.info("AccountDocs.getAccount");

      return ResponseDocs
          .data()

          .prefixDescription("사용자")

          .field(account.id, account.uuid, account.username, account.nickname,
              account.birth, account.createAt, account.updateAt)

          .linkField("account-gender", account.gender)

          .field("followingCount", "Following 수", NUMBER)
          .field("followerCount", "Follower 수", NUMBER)

          .prefixOptional()

          .field(account.introduce, account.signAt)

          .prefixDescription("사용자 프로필 이미지")
          .field(account.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("data.profile",
              "사용자 프로필 이미지", true))

          .prefixDescription("해시태그")
          .field(account.hashtagList, "목록", ARRAY)
          .field(account.hashtagList.any().hashtag, "정보", OBJECT)
          .field(account.hashtagList.any().hashtag.id)

          .clear()
          .build()
          .getFieldList();
    }

    public static List<FieldDescriptor> getAccountByManager() {
      log.info("AccountDocs.getAccountByManager");

      return ResponseDocs
          .data()

          .prefixDescription("사용자")

          .field(account.id, account.uuid, account.username, account.nickname,
              account.birth, account.createAt, account.updateAt)

          .linkField("account-status", account.status)
          .linkField("account-privilege", account.privilege)
          .linkField("account-gender", account.gender)

          .field("followingCount", "Following 수", NUMBER)
          .field("followerCount", "Follower 수", NUMBER)

          .prefixOptional()

          .field(account.introduce, account.signAt)

          .prefixDescription("사용자 프로필 이미지")
          .field(account.profile, "정보", OBJECT)
          .addField(DataDocs.Predefined.attachData("data.profile",
              "사용자 프로필 이미지", true))

          .prefixDescription("해시태그")
          .field(account.hashtagList, "목록", ARRAY)
          .field(account.hashtagList.any().hashtag, "정보", OBJECT)
          .field(account.hashtagList.any().hashtag.id)

          .clear()
          .build()
          .getFieldList();
    }
  }

  public static class Docs {
  }

}
