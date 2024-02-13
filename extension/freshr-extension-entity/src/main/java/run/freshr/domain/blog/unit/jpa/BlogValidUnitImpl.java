package run.freshr.domain.blog.unit.jpa;

import static run.freshr.common.utils.EntityValidateUtil.validateAuditorLogical;
import static run.freshr.common.utils.EntityValidateUtil.validatePhysical;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import run.freshr.common.annotations.Unit;
import run.freshr.domain.blog.entity.Blog;
import run.freshr.domain.blog.entity.Post;
import run.freshr.domain.blog.entity.PostComment;
import run.freshr.domain.blog.entity.embedded.BlogHashtagId;
import run.freshr.domain.blog.entity.embedded.BlogParticipateId;
import run.freshr.domain.blog.entity.embedded.BlogParticipateRequestId;
import run.freshr.domain.blog.entity.embedded.BlogSubscribeId;
import run.freshr.domain.blog.entity.embedded.PostAttachId;
import run.freshr.domain.blog.entity.embedded.PostCommentReactionId;
import run.freshr.domain.blog.entity.embedded.PostCommentWardId;
import run.freshr.domain.blog.entity.embedded.PostHashtagId;
import run.freshr.domain.blog.entity.embedded.PostReactionId;
import run.freshr.domain.blog.entity.mapping.BlogHashtag;
import run.freshr.domain.blog.entity.mapping.BlogParticipate;
import run.freshr.domain.blog.entity.mapping.BlogParticipateRequest;
import run.freshr.domain.blog.entity.mapping.BlogSubscribe;
import run.freshr.domain.blog.entity.mapping.PostAttach;
import run.freshr.domain.blog.entity.mapping.PostCommentReaction;
import run.freshr.domain.blog.entity.mapping.PostCommentWard;
import run.freshr.domain.blog.entity.mapping.PostHashtag;
import run.freshr.domain.blog.entity.mapping.PostReaction;
import run.freshr.domain.blog.repository.jpa.BlogHashtagValidRepository;
import run.freshr.domain.blog.repository.jpa.BlogParticipateRequestValidRepository;
import run.freshr.domain.blog.repository.jpa.BlogParticipateValidRepository;
import run.freshr.domain.blog.repository.jpa.BlogSubscribeValidRepository;
import run.freshr.domain.blog.repository.jpa.BlogValidRepository;
import run.freshr.domain.blog.repository.jpa.PostAttachValidRepository;
import run.freshr.domain.blog.repository.jpa.PostCommentReactionValidRepository;
import run.freshr.domain.blog.repository.jpa.PostCommentValidRepository;
import run.freshr.domain.blog.repository.jpa.PostCommentWardValidRepository;
import run.freshr.domain.blog.repository.jpa.PostHashtagValidRepository;
import run.freshr.domain.blog.repository.jpa.PostReactionValidRepository;
import run.freshr.domain.blog.repository.jpa.PostValidRepository;

@Slf4j
@Unit
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogValidUnitImpl implements BlogValidUnit {

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|
  private final BlogValidRepository blogValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public long createBlog(Blog entity) {
    return blogValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateBlog(Long id) {
    return validateAuditorLogical(blogValidRepository.findById(id));
  }

  @Override
  public Optional<Blog> getBlogOptional(Long id) {
    return blogValidRepository.findById(id);
  }

  @Override
  public Blog getBlog(Long id) {
    return blogValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______    __        ______     _______     __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \  |  |      /  __  \   /  _____|   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |______/  |_______| \______/   \______|    |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  private final BlogHashtagValidRepository blogHashtagValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public BlogHashtagId createBlogHashtag(BlogHashtag entity) {
    return blogHashtagValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateBlogHashtag(BlogHashtagId id) {
    return validatePhysical(blogHashtagValidRepository.findById(id));
  }

  @Override
  public Optional<BlogHashtag> getBlogHashtagOptional(BlogHashtagId id) {
    return blogHashtagValidRepository.findById(id);
  }

  @Override
  public BlogHashtag getBlogHashtag(BlogHashtagId id) {
    return blogHashtagValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______    .______       _______   ______      __    __   _______     _______.___________.
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|   |   _  \     |   ____| /  __  \    |  |  |  | |   ____|   /       |           |
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__      |  |_)  |    |  |__   |  |  |  |   |  |  |  | |  |__     |   (----`---|  |----`
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|     |      /     |   __|  |  |  |  |   |  |  |  | |   __|     \   \       |  |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____    |  |\  \----.|  |____ |  `--'  '--.|  `--'  | |  |____.----)   |      |  |
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|   | _| `._____||_______| \_____\_____\\______/  |_______|_______/       |__|
  private final BlogParticipateRequestValidRepository blogParticipateRequestValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public BlogParticipateRequestId createBlogParticipateRequest(BlogParticipateRequest entity) {
    return blogParticipateRequestValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateBlogParticipateRequest(BlogParticipateRequestId id) {
    return validatePhysical(blogParticipateRequestValidRepository.findById(id));
  }

  @Override
  public Optional<BlogParticipateRequest> getBlogParticipateRequestOptional(BlogParticipateRequestId id) {
    return blogParticipateRequestValidRepository.findById(id);
  }

  @Override
  public BlogParticipateRequest getBlogParticipateRequest(BlogParticipateRequestId id) {
    return blogParticipateRequestValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|
  private final BlogParticipateValidRepository blogParticipateValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public BlogParticipateId createBlogParticipate(BlogParticipate entity) {
    return blogParticipateValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateBlogParticipate(BlogParticipateId id) {
    return validatePhysical(blogParticipateValidRepository.findById(id));
  }

  @Override
  public Optional<BlogParticipate> getBlogParticipateOptional(BlogParticipateId id) {
    return blogParticipateValidRepository.findById(id);
  }

  @Override
  public BlogParticipate getBlogParticipate(BlogParticipateId id) {
    return blogParticipateValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______    __        ______     _______         _______. __    __  .______        _______.  ______ .______       __  .______    _______
  // |   _  \  |  |      /  __  \   /  _____|       /       ||  |  |  | |   _  \      /       | /      ||   _  \     |  | |   _  \  |   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __        |   (----`|  |  |  | |  |_)  |    |   (----`|  ,----'|  |_)  |    |  | |  |_)  | |  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |        \   \    |  |  |  | |   _  <      \   \    |  |     |      /     |  | |   _  <  |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    .----)   |   |  `--'  | |  |_)  | .----)   |   |  `----.|  |\  \----.|  | |  |_)  | |  |____
  // |______/  |_______| \______/   \______|    |_______/     \______/  |______/  |_______/     \______|| _| `._____||__| |______/  |_______|
  private final BlogSubscribeValidRepository blogSubscribeValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public BlogSubscribeId createBlogSubscribe(BlogSubscribe entity) {
    return blogSubscribeValidRepository.save(entity).getId();
  }

  @Override
  public boolean validateBlogSubscribe(BlogSubscribeId id) {
    return validatePhysical(blogSubscribeValidRepository.findById(id));
  }

  @Override
  public Optional<BlogSubscribe> getBlogSubscribeOptional(BlogSubscribeId id) {
    return blogSubscribeValidRepository.findById(id);
  }

  @Override
  public BlogSubscribe getBlogSubscribe(BlogSubscribeId id) {
    return blogSubscribeValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  private final PostValidRepository postValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public long createPost(Post entity) {
    return postValidRepository.save(entity).getId();
  }

  @Override
  public boolean validatePost(Long id) {
    return validateAuditorLogical(postValidRepository.findById(id));
  }

  @Override
  public Optional<Post> getPostOptional(Long id) {
    return postValidRepository.findById(id);
  }

  @Override
  public Post getPost(Long id) {
    return postValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______     ______        _______.___________.        ___   .___________.___________.    ___       __    __
  // |   _  \   /  __  \      /       |           |       /   \  |           |           |   /   \     |  |  |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`      /  ^  \ `---|  |----`---|  |----`  /  ^  \    |  |__|  |
  // |   ___/  |  |  |  |     \   \       |  |          /  /_\  \    |  |        |  |      /  /_\  \   |   __   |
  // |  |      |  `--'  | .----)   |      |  |         /  _____  \   |  |        |  |     /  _____  \  |  |  |  |
  // | _|       \______/  |_______/       |__|        /__/     \__\  |__|        |__|    /__/     \__\ |__|  |__|
  private final PostAttachValidRepository postAttachValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public PostAttachId createPostAttach(PostAttach entity) {
    return postAttachValidRepository.save(entity).getId();
  }

  @Override
  public boolean validatePostAttach(PostAttachId id) {
    return validatePhysical(postAttachValidRepository.findById(id));
  }

  @Override
  public Optional<PostAttach> getPostAttachOptional(PostAttachId id) {
    return postAttachValidRepository.findById(id);
  }

  @Override
  public PostAttach getPostAttach(PostAttachId id) {
    return postAttachValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______     ______        _______.___________.    __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \   /  __  \      /       |           |   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   ___/  |  |  |  |     \   \       |  |        |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |      |  `--'  | .----)   |      |  |        |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // | _|       \______/  |_______/       |__|        |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  private final PostHashtagValidRepository postHashtagValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public PostHashtagId createPostHashtag(PostHashtag entity) {
    return postHashtagValidRepository.save(entity).getId();
  }

  @Override
  public boolean validatePostHashtag(PostHashtagId id) {
    return validatePhysical(postHashtagValidRepository.findById(id));
  }

  @Override
  public Optional<PostHashtag> getPostHashtagOptional(PostHashtagId id) {
    return postHashtagValidRepository.findById(id);
  }

  @Override
  public PostHashtag getPostHashtag(PostHashtagId id) {
    return postHashtagValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______     ______        _______.___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  private final PostReactionValidRepository postReactionValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public PostReactionId createPostReaction(PostReaction entity) {
    return postReactionValidRepository.save(entity).getId();
  }

  @Override
  public boolean validatePostReaction(PostReactionId id) {
    return validatePhysical(postReactionValidRepository.findById(id));
  }

  @Override
  public Optional<PostReaction> getPostReactionOptional(PostReactionId id) {
    return postReactionValidRepository.findById(id);
  }

  @Override
  public PostReaction getPostReaction(PostReactionId id) {
    return postReactionValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|
  private final PostCommentValidRepository postCommentValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public long createPostComment(PostComment entity) {
    return postCommentValidRepository.save(entity).getId();
  }

  @Override
  public boolean validatePostComment(Long id) {
    return validateAuditorLogical(postCommentValidRepository.findById(id));
  }

  @Override
  public Optional<PostComment> getPostCommentOptional(Long id) {
    return postCommentValidRepository.findById(id);
  }

  @Override
  public PostComment getPostComment(Long id) {
    return postCommentValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  private final PostCommentReactionValidRepository postCommentReactionValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public PostCommentReactionId createPostCommentReaction(PostCommentReaction entity) {
    return postCommentReactionValidRepository.save(entity).getId();
  }

  @Override
  public boolean validatePostCommentReaction(PostCommentReactionId id) {
    return validatePhysical(postCommentReactionValidRepository.findById(id));
  }

  @Override
  public Optional<PostCommentReaction> getPostCommentReactionOptional(PostCommentReactionId id) {
    return postCommentReactionValidRepository.findById(id);
  }

  @Override
  public PostCommentReaction getPostCommentReaction(PostCommentReactionId id) {
    return postCommentReactionValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   ____    __    ____  ___      .______       _______
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   \   \  /  \  /   / /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`    \   \/    \/   / /  ^  \    |  |_)  |    |  .--.  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |          \            / /  /_\  \   |      /     |  |  |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |           \    /\    / /  _____  \  |  |\  \----.|  '--'  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|            \__/  \__/ /__/     \__\ | _| `._____||_______/
  private final PostCommentWardValidRepository postCommentWardValidRepository;

  @Profile("test")
  @Override
  @Transactional
  public PostCommentWardId createPostCommentWard(PostCommentWard entity) {
    return postCommentWardValidRepository.save(entity).getId();
  }

  @Override
  public boolean validatePostCommentWard(PostCommentWardId id) {
    return validatePhysical(postCommentWardValidRepository.findById(id));
  }

  @Override
  public Optional<PostCommentWard> getPostCommentWardOptional(PostCommentWardId id) {
    return postCommentWardValidRepository.findById(id);
  }

  @Override
  public PostCommentWard getPostCommentWard(PostCommentWardId id) {
    return postCommentWardValidRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

}
