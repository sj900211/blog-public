package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.PostAttachId;
import run.freshr.domain.blog.entity.mapping.PostAttach;

public interface PostAttachValidRepository extends JpaRepository<PostAttach, PostAttachId> {

}
