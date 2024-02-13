package run.freshr.domain.statistic.elasticsearch;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Slf4j
@Document(indexName = "statistic-hashtag")
@Getter
@NoArgsConstructor(access = PROTECTED)
@Comment("해시태그 관리")
public class StatisticHashtag {

  @Id
  @Field(fielddata = true)
  @Comment("일련 번호")
  private String id;

  @Comment("계정 수")
  private Long accountCount;

  @Comment("블로그 수")
  private Long blogCount;

  @Comment("포스팅 수")
  private Long postCount;

  @Builder
  public StatisticHashtag(String id, Long accountCount, Long blogCount, Long postCount) {
    log.info("StatisticHashtag.Constructor");

    this.id = id;
    this.accountCount = accountCount;
    this.blogCount = blogCount;
    this.postCount = postCount;
  }

  public void updateAccountCount(long accountCount) {
    log.info("StatisticHashtag.updateAccountCount");

    this.accountCount = accountCount;
  }

  public void updateBlogCount(long blogCount) {
    log.info("StatisticHashtag.updateBlogCount");

    this.blogCount = blogCount;
  }

  public void updatePostCount(long postCount) {
    log.info("StatisticHashtag.updatePostCount");

    this.postCount = postCount;
  }

}
