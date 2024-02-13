package run.freshr.domain.account.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.InheritanceType.JOINED;
import static lombok.AccessLevel.PROTECTED;
import static run.freshr.common.configurations.DefaultColumnConfig.FALSE;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import run.freshr.common.extensions.entity.EntityAuditPhysicalExtension;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.enumerations.AccountNotificationType;

@Slf4j
@Entity
@Table(
    schema = "account",
    name = "ACCOUNT_NOTIFICATION",
    indexes = {
        @Index(name = "IDX_ACCOUNT_NOTIFICATION_READ", columnList = "read"),
        @Index(name = "IDX_ACCOUNT_NOTIFICATION_DEFAULT_AT", columnList = "createAt")
    }
)
@Getter
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "division")
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = PROTECTED)
@Comment("사용자 관리 > 알림 관리")
public class AccountNotification extends EntityAuditPhysicalExtension<Account> {

    @Id
    @Comment("일련 번호")
    protected String id;

    @Enumerated(STRING)
    @Column(nullable = false)
    @Comment("유형")
    protected AccountNotificationType type;

    @ColumnDefault(FALSE)
    @Comment("읽음 여부")
    protected Boolean read;

    @Enumerated(STRING)
    @Column(insertable = false, updatable = false)
    @Comment("유형")
    protected AccountNotificationInheritanceType division;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "account_id",
        foreignKey = @ForeignKey(name = "FK_ACCOUNT_NOTIFICATION_ACCOUNT"))
    @Comment("계정 정보")
    protected Account account;

}
