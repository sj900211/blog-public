package run.freshr.domain.logging.elasticsearch;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.common.data.IdDocumentData;
import run.freshr.domain.logging.elasticsearch.data.LogAccountData;
import run.freshr.domain.logging.elasticsearch.extensions.LogExtension;
import run.freshr.domain.logging.enumerations.LogAccountType;

@Slf4j
@Document(indexName = "log-account")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Comment("계정 로그 관리")
public class LogAccount extends LogExtension<String, LogAccountType, LogAccountData> {

  @Field(type = FieldType.Object)
  @Comment("사용자 일련 번호")
  private IdDocumentData<Long> account;

  @Builder
  public LogAccount(String id, IdDocumentData<Long> account, LogAccountType type,
      LogAccountData before, LogAccountData after, IdDocumentData<Long> scribe) {
    log.info("LogAccount.Constructor");

    this.id = id;
    this.account = account;
    this.type = type;
    this.before = before;
    this.after = after;
    this.scribe = scribe;
  }

}
