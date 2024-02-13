package run.freshr.common.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * Cursor 기반의 response 모델 정의
 *
 * @param <T> Content 데이터 유형
 * @author 류성재
 * @apiNote Cursor 기능을 사용할 때 Page 객체에 Cursor 정보를 추가
 * @since 2023. 6. 15. 오후 1:34:45
 */
@Getter
public class CursorData<T> extends PageImpl<T> implements Page<T> {

  /**
   * Cursor
   *
   * @apiNote Cursor 값
   * @since 2023. 6. 15. 오후 1:34:45
   */
  private final Long cursor;

  @JsonCreator
  public CursorData(List<T> content, Pageable pageable, long total, Long cursor) {
    super(content, pageable, total);

    this.cursor = cursor;
  }

  @Override
  public <U> CursorData<U> map(Function<? super T, ? extends U> converter) {
    return new CursorData<>(getConvertedContent(converter), getPageable(),
        getTotalElements(), cursor);
  }

}
