package run.freshr.domain.predefined.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extensions.entity.EntityPhysicalExtension;

@Slf4j
@Entity
@Table(
    schema = "predefined",
    name = "PREDEFINED_HASHTAG",
    indexes = {
        @Index(name = "IDX_PREDEFINED_HASHTAG_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("사전 정의 관리 > 해시태그 관리")
public class Hashtag extends EntityPhysicalExtension {

  @Id
  @Comment("일련 번호")
  private String id;

  @Builder
  public Hashtag(String id) {
    log.info("Hashtag.Constructor");

    this.id = id;
  }

}
