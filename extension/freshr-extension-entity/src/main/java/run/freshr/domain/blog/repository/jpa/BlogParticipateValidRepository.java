package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.BlogHashtagId;
import run.freshr.domain.blog.entity.embedded.BlogParticipateId;
import run.freshr.domain.blog.entity.mapping.BlogHashtag;
import run.freshr.domain.blog.entity.mapping.BlogParticipate;

public interface BlogParticipateValidRepository extends
    JpaRepository<BlogParticipate, BlogParticipateId> {

}
