package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.PostCommentReactionId;
import run.freshr.domain.blog.entity.mapping.PostCommentReaction;

public interface PostCommentReactionValidRepository extends
    JpaRepository<PostCommentReaction, PostCommentReactionId> {
}
