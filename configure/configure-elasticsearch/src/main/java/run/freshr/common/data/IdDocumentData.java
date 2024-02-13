package run.freshr.common.data;

import static lombok.AccessLevel.PROTECTED;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class IdDocumentData<ID> {

  private ID id;

  @Builder
  public IdDocumentData(ID id) {
    this.id = id;
  }

}
