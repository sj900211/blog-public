package run.freshr.common.data;

import lombok.Builder;
import lombok.Data;

/**
 * Docs Annotation 을 적용한 클래스의 정보
 *
 * @author 류성재
 * @apiNote Docs Annotation 을 적용한 클래스의 정보를<br>
 *          가공하기 편리하도록 정의한 클래스
 * @since 2023. 1. 12. 오후 3:23:19
 */
@Data
@Builder
public class DocsData {

  /**
   * include 경로
   *
   * @apiNote 메서드 이름을 lower-hyphen 으로 변환한 데이터
   * @since 2023. 1. 12. 오후 3:23:19
   */
  private String path;
  /**
   * 설명
   *
   * @apiNote DisplayName Annotation 정보를 읽어서 저장
   * @since 2023. 1. 12. 오후 3:23:19
   */
  private String description;
  private Boolean existsPathParameters;
  private Boolean existsRequestParts;
  private Boolean existsQueryParameters;
  private Boolean existsFormParameters;
  private Boolean existsRequestFields;
  private Boolean existsResponseFields;

}
