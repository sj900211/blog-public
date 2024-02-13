package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.BlogSubscribeId;
import run.freshr.domain.blog.entity.mapping.BlogSubscribe;

public interface BlogSubscribeValidRepository extends
    JpaRepository<BlogSubscribe, BlogSubscribeId> {

}
