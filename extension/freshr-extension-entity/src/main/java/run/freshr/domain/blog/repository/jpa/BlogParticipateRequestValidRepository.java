package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.BlogParticipateRequestId;
import run.freshr.domain.blog.entity.mapping.BlogParticipateRequest;

public interface BlogParticipateRequestValidRepository extends
    JpaRepository<BlogParticipateRequest, BlogParticipateRequestId> {

}
