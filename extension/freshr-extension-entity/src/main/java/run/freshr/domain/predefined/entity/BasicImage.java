package run.freshr.domain.predefined.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.configurations.DefaultColumnConfig.ZERO;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extensions.entity.EntityAuditLogicalExtension;
import run.freshr.domain.account.entity.Account;
import run.freshr.domain.predefined.enumerations.BasicImageType;

@Slf4j
@Entity
@Table(
    schema = "predefined",
    name = "PREDEFINED_BASIC_IMAGE",
    indexes = {
        @Index(name = "IDX_PREDEFINED_BASIC_IMAGE_DEFAULT_FLAG",
            columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_PREDEFINED_BASIC_IMAGE_DEFAULT_AT", columnList = "createAt")
    }
)
@AssociationOverrides({
    @AssociationOverride(name = "creator",
        joinColumns = @JoinColumn(name = "creator_id"),
        foreignKey = @ForeignKey(name = "FK_PREDEFINED_BASIC_IMAGE_CREATOR")),
    @AssociationOverride(name = "updater",
        joinColumns = @JoinColumn(name = "updater_id"),
        foreignKey = @ForeignKey(name = "FK_PREDEFINED_BASIC_IMAGE_UPDATER"))
})
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("사전 정의 관리 > 기본 이미지 관리")
public class BasicImage extends EntityAuditLogicalExtension<Account> {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @ColumnDefault(ZERO)
  @Comment("정렬 순서")
  private Integer sort;

  @Enumerated(STRING)
  @Column(nullable = false)
  @Comment("유형")
  private BasicImageType type;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "image_id",
      foreignKey = @ForeignKey(name = "FK_PREDEFINED_BASIC_IMAGE_ATTACH"))
  @Comment("이미지")
  private Attach image;

  @Builder
  public BasicImage(Integer sort, BasicImageType type, Attach image) {
    log.info("BasicImage.Constructor");

    this.sort = sort;
    this.type = type;
    this.image = image;
  }

  public void updateEntity(Integer sort, Attach image) {
    log.info("BasicImage.updateEntity");

    this.sort = sort;
    this.image = image;
  }

  public void removeEntity() {
    log.info("BasicImage.removeEntity");

    remove();
  }

}
