package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.PostHashtagId;
import run.freshr.domain.blog.entity.mapping.PostHashtag;

public interface PostHashtagValidRepository extends JpaRepository<PostHashtag, PostHashtagId> {

}
