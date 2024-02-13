package run.freshr.domain.predefined.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.configurations.DefaultColumnConfig.FALSE;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extensions.entity.EntityAuditLogicalExtension;
import run.freshr.common.unit.MinioUnit;
import run.freshr.common.utils.BeanUtil;
import run.freshr.domain.account.entity.Account;

@Slf4j
@Entity
@Table(
    schema = "predefined",
    name = "PREDEFINED_ATTACH",
    indexes = {
        @Index(name = "IDX_PREDEFINED_ATTACH_DEFAULT_FLAG", columnList = "useFlag, deleteFlag"),
        @Index(name = "IDX_PREDEFINED_ATTACH_DEFAULT_AT", columnList = "createAt")
    }
)
@AssociationOverrides({
    @AssociationOverride(name = "creator",
        joinColumns = @JoinColumn(name = "creator_id"),
        foreignKey = @ForeignKey(name = "FK_PREDEFINED_ATTACH_CREATOR")),
    @AssociationOverride(name = "updater",
        joinColumns = @JoinColumn(name = "updater_id"),
        foreignKey = @ForeignKey(name = "FK_PREDEFINED_ATTACH_UPDATER"))
})
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("사전 정의 관리 > 파일 관리")
public class Attach extends EntityAuditLogicalExtension<Account> {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Comment("일련 번호")
  private Long id;

  @Comment("파일 유형")
  private String contentType;

  @Column(nullable = false)
  @Comment("파일 이름")
  private String filename;

  @Column(nullable = false)
  @Comment("파일 키")
  private String key;

  @Column(nullable = false)
  @Comment("파일 크기")
  private Long size;

  @Comment("대체 문구")
  private String alt;

  @Comment("제목")
  private String title;

  @ColumnDefault(FALSE)
  @Comment("공개 여부")
  private Boolean isPublic;

  @Transient
  private URL url;

  @Builder
  public Attach(String contentType, String filename, String key, Long size,
      String alt, String title, Boolean isPublic) {
    log.info("Attach.Constructor");

    this.contentType = contentType;
    this.filename = filename;
    this.key = key;
    this.size = size;
    this.alt = alt;
    this.title = title;
    this.isPublic = isPublic;
  }

  public void changeIsPublic() {
    log.info("Attach.changeIsPublic");

    this.isPublic = true;
  }

  public void changeIsNotPublic() {
    log.info("Attach.changeIsNotPublic");

    this.isPublic = false;
  }

  public void removeEntity() {
    log.info("Attach.removeEntity");

    remove();
  }

  public URL getUrl() throws IOException, InsufficientDataException, ErrorResponseException,
      NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException,
      InternalException, ServerException {
    MinioUnit unit = BeanUtil.getBean(MinioUnit.class);

    return unit.getUrl(this.key);
  }

}
