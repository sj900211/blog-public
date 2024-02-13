package run.freshr.common.extensions.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 물리 삭제 MappedSuperclass
 *
 * @author 류성재
 * @apiNote 물리 삭제 정책을 가진 MappedSuperclass
 * @since 2023. 6. 15. 오후 1:40:56
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityPhysicalExtension {

  @CreatedDate
  @Comment("등록 날짜 시간")
  protected LocalDateTime createAt;

}
