package run.freshr.domain.predefined.repository.jpa;

import static org.springframework.util.StringUtils.hasLength;
import static run.freshr.domain.predefined.entity.QHashtag.hashtag;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import run.freshr.common.data.CursorData;
import run.freshr.common.utils.QueryUtilAware;
import run.freshr.domain.predefined.entity.Hashtag;
import run.freshr.domain.predefined.vo.PredefinedStringSearch;

@RequiredArgsConstructor
public class HashtagRepositoryImpl implements HashtagRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<Hashtag> search(PredefinedStringSearch search) {
    String word = search.getWord();

    JPAQuery<Hashtag> query = queryFactory
        .selectFrom(hashtag)
        .where(hashtag.id.containsIgnoreCase(word))
        .orderBy(hashtag.id.asc(), hashtag.createAt.desc());

    return query.fetch();
  }

  @Override
  public CursorData<Hashtag> getPage(PredefinedStringSearch search) {
    LocalDateTime cursorAt = search.getCursorAt();

    JPAQuery<Hashtag> query = queryFactory
        .selectFrom(hashtag)
        .where(hashtag.createAt.before(cursorAt));

    String word = search.getWord();

    if (hasLength(word)) {
      query.where(hashtag.id.containsIgnoreCase(word));
    }

    return QueryUtilAware.paging(query, hashtag, search);
  }

}
