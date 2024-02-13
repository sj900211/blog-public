package run.freshr.domain.blog.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import run.freshr.domain.blog.entity.PostComment;

public interface PostCommentValidRepository extends JpaRepository<PostComment, Long> {

}
