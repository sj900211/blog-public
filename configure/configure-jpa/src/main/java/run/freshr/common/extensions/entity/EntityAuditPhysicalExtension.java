package run.freshr.common.extensions.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Auditor 정보를 갖는 물리 삭제 MappedSuperclass
 *
 * @param <A> Auditor 데이터 유형
 * @author 류성재
 * @apiNote Audit 정보와 물리 삭제 정책을 가진 MappedSuperclass
 * @since 2023. 6. 15. 오후 1:40:13
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityAuditPhysicalExtension<A> {

  @CreatedDate
  @Comment("등록 날짜 시간")
  protected LocalDateTime createAt;

  @CreatedBy
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "creator_id")
  @Comment("등록자 일련 번호")
  protected A creator;

}
