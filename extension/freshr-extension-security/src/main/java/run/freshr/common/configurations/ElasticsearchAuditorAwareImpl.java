package run.freshr.common.configurations;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchAuditorAwareImpl implements AuditorAware<Void> {

  @NotNull
  @Override
  public Optional<Void> getCurrentAuditor() {
    return Optional.empty();
  }

}
