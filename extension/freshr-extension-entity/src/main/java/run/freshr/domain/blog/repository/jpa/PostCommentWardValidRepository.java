package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.embedded.PostCommentWardId;
import run.freshr.domain.blog.entity.mapping.PostCommentWard;

public interface PostCommentWardValidRepository extends
    JpaRepository<PostCommentWard, PostCommentWardId> {
}
