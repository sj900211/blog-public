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
import run.freshr.domain.logging.elasticsearch.data.LogChannelData;
import run.freshr.domain.logging.elasticsearch.extensions.LogExtension;
import run.freshr.domain.logging.enumerations.LogChannelType;

@Slf4j
@Document(indexName = "log-channel")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Comment("채널 로그 관리")
public class LogChannel extends LogExtension<String, LogChannelType, LogChannelData> {

  @Field(type = FieldType.Object)
  @Comment("채널 일련 번호")
  private IdDocumentData<Long> channel;

  @Builder
  public LogChannel(String id, IdDocumentData<Long> channel, LogChannelType type,
      LogChannelData before, LogChannelData after, IdDocumentData<Long> scribe) {
    log.info("LogChannel.Constructor");

    this.id = id;
    this.channel = channel;
    this.type = type;
    this.before = before;
    this.after = after;
    this.scribe = scribe;
  }

}
