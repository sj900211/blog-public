package run.freshr.domain.account.dto.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import run.freshr.common.extensions.response.ResponsePhysicalExtension;
import run.freshr.domain.account.dto.data.AccountSimpleData;
import run.freshr.domain.account.enumerations.AccountNotificationInheritanceType;
import run.freshr.domain.account.enumerations.AccountNotificationType;
import run.freshr.domain.blog.dto.data.BlogSimpleData;
import run.freshr.domain.blog.dto.data.PostCommentSimpleData;
import run.freshr.domain.blog.dto.data.PostSimpleData;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@EqualsAndHashCode(callSuper = true)
public class AccountNotificationListResponse extends ResponsePhysicalExtension<String> {

  private AccountNotificationInheritanceType division;
  private AccountNotificationType type;
  private Boolean read;
  private AccountSimpleData person;
  private BlogSimpleData blog;
  private PostSimpleData post;
  private PostCommentSimpleData postComment;

}
