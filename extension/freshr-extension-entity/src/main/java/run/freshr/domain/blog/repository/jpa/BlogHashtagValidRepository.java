package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.BlogHashtagId;
import run.freshr.domain.blog.entity.mapping.BlogHashtag;

public interface BlogHashtagValidRepository extends JpaRepository<BlogHashtag, BlogHashtagId> {

}
