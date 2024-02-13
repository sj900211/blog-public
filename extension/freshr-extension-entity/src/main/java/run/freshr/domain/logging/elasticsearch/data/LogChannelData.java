package run.freshr.domain.logging.elasticsearch.data;

import static lombok.AccessLevel.PROTECTED;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import run.freshr.common.data.IdDocumentData;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class LogChannelData {

  @Field(type = FieldType.Nested)
  private Set<IdDocumentData<Long>> accountList;

  @Builder
  public LogChannelData(Set<IdDocumentData<Long>> accountList) {
    this.accountList = accountList;
  }

}
