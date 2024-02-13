package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.PostReactionId;
import run.freshr.domain.blog.entity.mapping.PostReaction;

public interface PostReactionValidRepository extends JpaRepository<PostReaction, PostReactionId> {

}
