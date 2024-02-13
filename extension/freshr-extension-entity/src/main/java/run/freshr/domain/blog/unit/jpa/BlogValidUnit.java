package run.freshr.domain.blog.unit.jpa;

import java.util.Optional;
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

public interface BlogValidUnit {

  // .______    __        ______     _______
  // |   _  \  |  |      /  __  \   /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |
  // |______/  |_______| \______/   \______|
  long createBlog(Blog entity);

  boolean validateBlog(Long id);

  Optional<Blog> getBlogOptional(Long id);

  Blog getBlog(Long id);

  // .______    __        ______     _______     __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \  |  |      /  __  \   /  _____|   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // |______/  |_______| \______/   \______|    |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  BlogHashtagId createBlogHashtag(BlogHashtag entity);

  boolean validateBlogHashtag(BlogHashtagId id);

  Optional<BlogHashtag> getBlogHashtagOptional(BlogHashtagId id);

  BlogHashtag getBlogHashtag(BlogHashtagId id);

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______    .______       _______   ______      __    __   _______     _______.___________.
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|   |   _  \     |   ____| /  __  \    |  |  |  | |   ____|   /       |           |
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__      |  |_)  |    |  |__   |  |  |  |   |  |  |  | |  |__     |   (----`---|  |----`
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|     |      /     |   __|  |  |  |  |   |  |  |  | |   __|     \   \       |  |
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____    |  |\  \----.|  |____ |  `--'  '--.|  `--'  | |  |____.----)   |      |  |
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|   | _| `._____||_______| \_____\_____\\______/  |_______|_______/       |__|
  BlogParticipateRequestId createBlogParticipateRequest(BlogParticipateRequest entity);

  boolean validateBlogParticipateRequest(BlogParticipateRequestId id);

  Optional<BlogParticipateRequest> getBlogParticipateRequestOptional(BlogParticipateRequestId id);

  BlogParticipateRequest getBlogParticipateRequest(BlogParticipateRequestId id);

  // .______    __        ______     _______    .______      ___      .______     .___________. __    ______  __  .______      ___   .___________. _______
  // |   _  \  |  |      /  __  \   /  _____|   |   _  \    /   \     |   _  \    |           ||  |  /      ||  | |   _  \    /   \  |           ||   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __     |  |_)  |  /  ^  \    |  |_)  |   `---|  |----`|  | |  ,----'|  | |  |_)  |  /  ^  \ `---|  |----`|  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |    |   ___/  /  /_\  \   |      /        |  |     |  | |  |     |  | |   ___/  /  /_\  \    |  |     |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    |  |     /  _____  \  |  |\  \----.   |  |     |  | |  `----.|  | |  |     /  _____  \   |  |     |  |____
  // |______/  |_______| \______/   \______|    | _|    /__/     \__\ | _| `._____|   |__|     |__|  \______||__| | _|    /__/     \__\  |__|     |_______|
  BlogParticipateId createBlogParticipate(BlogParticipate entity);

  boolean validateBlogParticipate(BlogParticipateId id);

  Optional<BlogParticipate> getBlogParticipateOptional(BlogParticipateId id);

  BlogParticipate getBlogParticipate(BlogParticipateId id);

  // .______    __        ______     _______         _______. __    __  .______        _______.  ______ .______       __  .______    _______
  // |   _  \  |  |      /  __  \   /  _____|       /       ||  |  |  | |   _  \      /       | /      ||   _  \     |  | |   _  \  |   ____|
  // |  |_)  | |  |     |  |  |  | |  |  __        |   (----`|  |  |  | |  |_)  |    |   (----`|  ,----'|  |_)  |    |  | |  |_)  | |  |__
  // |   _  <  |  |     |  |  |  | |  | |_ |        \   \    |  |  |  | |   _  <      \   \    |  |     |      /     |  | |   _  <  |   __|
  // |  |_)  | |  `----.|  `--'  | |  |__| |    .----)   |   |  `--'  | |  |_)  | .----)   |   |  `----.|  |\  \----.|  | |  |_)  | |  |____
  // |______/  |_______| \______/   \______|    |_______/     \______/  |______/  |_______/     \______|| _| `._____||__| |______/  |_______|
  BlogSubscribeId createBlogSubscribe(BlogSubscribe entity);

  boolean validateBlogSubscribe(BlogSubscribeId id);

  Optional<BlogSubscribe> getBlogSubscribeOptional(BlogSubscribeId id);

  BlogSubscribe getBlogSubscribe(BlogSubscribeId id);

  // .______     ______        _______.___________.
  // |   _  \   /  __  \      /       |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |
  // |  |      |  `--'  | .----)   |      |  |
  // | _|       \______/  |_______/       |__|
  long createPost(Post entity);

  boolean validatePost(Long id);

  Optional<Post> getPostOptional(Long id);

  Post getPost(Long id);

  // .______     ______        _______.___________.        ___   .___________.___________.    ___       __    __
  // |   _  \   /  __  \      /       |           |       /   \  |           |           |   /   \     |  |  |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`      /  ^  \ `---|  |----`---|  |----`  /  ^  \    |  |__|  |
  // |   ___/  |  |  |  |     \   \       |  |          /  /_\  \    |  |        |  |      /  /_\  \   |   __   |
  // |  |      |  `--'  | .----)   |      |  |         /  _____  \   |  |        |  |     /  _____  \  |  |  |  |
  // | _|       \______/  |_______/       |__|        /__/     \__\  |__|        |__|    /__/     \__\ |__|  |__|
  PostAttachId createPostAttach(PostAttach entity);

  boolean validatePostAttach(PostAttachId id);

  Optional<PostAttach> getPostAttachOptional(PostAttachId id);

  PostAttach getPostAttach(PostAttachId id);

  // .______     ______        _______.___________.    __    __       ___           _______. __    __  .___________.    ___       _______
  // |   _  \   /  __  \      /       |           |   |  |  |  |     /   \         /       ||  |  |  | |           |   /   \     /  _____|
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |__|  |    /  ^  \       |   (----`|  |__|  | `---|  |----`  /  ^  \   |  |  __
  // |   ___/  |  |  |  |     \   \       |  |        |   __   |   /  /_\  \       \   \    |   __   |     |  |      /  /_\  \  |  | |_ |
  // |  |      |  `--'  | .----)   |      |  |        |  |  |  |  /  _____  \  .----)   |   |  |  |  |     |  |     /  _____  \ |  |__| |
  // | _|       \______/  |_______/       |__|        |__|  |__| /__/     \__\ |_______/    |__|  |__|     |__|    /__/     \__\ \______|
  PostHashtagId createPostHashtag(PostHashtag entity);

  boolean validatePostHashtag(PostHashtagId id);

  Optional<PostHashtag> getPostHashtagOptional(PostHashtagId id);

  PostHashtag getPostHashtag(PostHashtagId id);

  // .______     ______        _______.___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  PostReactionId createPostReaction(PostReaction entity);

  boolean validatePostReaction(PostReactionId id);

  Optional<PostReaction> getPostReactionOptional(PostReactionId id);

  PostReaction getPostReaction(PostReactionId id);

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|
  long createPostComment(PostComment entity);

  boolean validatePostComment(Long id);

  Optional<PostComment> getPostCommentOptional(Long id);

  PostComment getPostComment(Long id);

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   .______       _______     ___       ______ .___________. __    ______   .__   __.
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   |   _  \     |   ____|   /   \     /      ||           ||  |  /  __  \  |  \ |  |
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`   |  |_)  |    |  |__     /  ^  \   |  ,----'`---|  |----`|  | |  |  |  | |   \|  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |        |      /     |   __|   /  /_\  \  |  |         |  |     |  | |  |  |  | |  . `  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |        |  |\  \----.|  |____ /  _____  \ |  `----.    |  |     |  | |  `--'  | |  |\   |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|        | _| `._____||_______/__/     \__\ \______|    |__|     |__|  \______/  |__| \__|
  PostCommentReactionId createPostCommentReaction(PostCommentReaction entity);

  boolean validatePostCommentReaction(PostCommentReactionId id);

  Optional<PostCommentReaction> getPostCommentReactionOptional(PostCommentReactionId id);

  PostCommentReaction getPostCommentReaction(PostCommentReactionId id);

  // .______     ______        _______.___________.     ______   ______   .___  ___. .___  ___.  _______ .__   __. .___________.   ____    __    ____  ___      .______       _______
  // |   _  \   /  __  \      /       |           |    /      | /  __  \  |   \/   | |   \/   | |   ____||  \ |  | |           |   \   \  /  \  /   / /   \     |   _  \     |       \
  // |  |_)  | |  |  |  |    |   (----`---|  |----`   |  ,----'|  |  |  | |  \  /  | |  \  /  | |  |__   |   \|  | `---|  |----`    \   \/    \/   / /  ^  \    |  |_)  |    |  .--.  |
  // |   ___/  |  |  |  |     \   \       |  |        |  |     |  |  |  | |  |\/|  | |  |\/|  | |   __|  |  . `  |     |  |          \            / /  /_\  \   |      /     |  |  |  |
  // |  |      |  `--'  | .----)   |      |  |        |  `----.|  `--'  | |  |  |  | |  |  |  | |  |____ |  |\   |     |  |           \    /\    / /  _____  \  |  |\  \----.|  '--'  |
  // | _|       \______/  |_______/       |__|         \______| \______/  |__|  |__| |__|  |__| |_______||__| \__|     |__|            \__/  \__/ /__/     \__\ | _| `._____||_______/
  PostCommentWardId createPostCommentWard(PostCommentWard entity);

  boolean validatePostCommentWard(PostCommentWardId id);

  Optional<PostCommentWard> getPostCommentWardOptional(PostCommentWardId id);

  PostCommentWard getPostCommentWard(PostCommentWardId id);

}
