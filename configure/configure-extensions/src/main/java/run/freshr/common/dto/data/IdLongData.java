package run.freshr.common.dto.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.freshr.common.dto.response.IdResponse;

/**
 * Long PK DTO
 *
 * @author FreshR
 * @apiNote 필드 이름이 id 인 경우 공통으로 사용하기 위한 반환 모델로
 *          {@link IdResponse} 를 만들었지만
 *          2023-10-06 테스트 중 ModelMapper 에서 변환이 되지 않아
 *          객체가 null 로 반환되는 것을 확인 및 검증
 *          Generic 으로 선언한 경우 어떤 이유에서 변환되지 않는지 원인 파악 못했음
 *          일단 Long, String 두 가지 유형의 DTO 를 생성해서 사용하는 것으로 현상은 해결
 * @since 2023. 10. 6. 오후 4:03:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdLongData {

  private Long id;

}
