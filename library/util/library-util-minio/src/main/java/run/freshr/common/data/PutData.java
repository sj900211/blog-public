package run.freshr.common.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Put result response.
 *
 * @author FreshR
 * @apiNote api note
 * @since 2022. 12. 24. 오후 3:25:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PutData {

  /**
   * Filename
   *
   * @apiNote 파일 이름
   * @since 2022. 12. 24. 오후 3:25:50
   */
  private String filename;
  /**
   * Physical
   *
   * @apiNote 파일 물리 저장 이름
   * @since 2022. 12. 24. 오후 3:25:50
   */
  private String physical;
  /**
   * Content type
   *
   * @apiNote 파일 유형
   * @since 2022. 12. 24. 오후 3:25:50
   */
  private String contentType;
  /**
   * Size
   *
   * @apiNote 파일 크기
   * @since 2022. 12. 24. 오후 3:25:50
   */
  private Long size;

}
