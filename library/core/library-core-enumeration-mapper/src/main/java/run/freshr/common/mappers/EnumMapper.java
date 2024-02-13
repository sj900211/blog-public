package run.freshr.common.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 열거형 데이터 Mapper
 *
 * @author 류성재
 * @apiNote 정의된 열거형 데이터 클래스 목록을<br>
 *          키, 값 형태로 제작하도록 설정
 * @since 2022. 12. 23. 오후 4:23:59
 */
public class EnumMapper {

  /**
   * 열거형 데이터 Factory
   *
   * @apiNote 열거형 데이터 목록을 키, 값 형태로 저장
   * @since 2022. 12. 23. 오후 4:23:59
   */
  Map<String, List<EnumGetter>> factory = new HashMap<>();

  /**
   * 열거형 데이터를 열거형 데이터 반환 객체 목록으로 변환
   *
   * @param e 열거형 데이터 클래스
   * @return 열거형 데이터 목록
   * @apiNote 열거형 데이터 클래스의 항목을 열거형 데이터 반환 객체 목록으로 반환
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  List<EnumGetter> toEnumGetters(final Class<? extends EnumModel> e) {
    return Arrays
        .stream(e.getEnumConstants())
        .map(EnumGetter::new)
        .collect(Collectors.toList());
  }

  /**
   * 열거형 데이터 반환 객체를 factory 에 저장
   *
   * @param key 키 문자
   * @param e   열거형 데이터 반환 객체
   * @apiNote 열거형 데이터 반환 객체를 factory 에 저장
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public void put(final String key, final Class<? extends EnumModel> e) {
    factory.put(key, toEnumGetters(e));
  }

  /**
   * 저장된 전체 열거형 데이터 조회
   *
   * @return 열거형 데이터 Factory
   * @apiNote 저장된 전체 열거형 데이터 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public Map<String, List<EnumGetter>> getAll() {
    return factory;
  }

  /**
   * 열거형 데이터 조회
   *
   * @param keys 키 문자
   * @return 열거형 데이터
   * @apiNote 하나의 열거형 클래스를 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public Map<String, List<EnumGetter>> get(final String keys) {
    return Arrays
        .stream(keys.split(","))
        .collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
  }

  /**
   * 열거형 데이터 목록 조회
   *
   * @param enumKey 열거형 데이터 키 문자
   * @return 열거형 데이터 반환 객체 목록 조회
   * @apiNote 열거형 데이터 반환 객체 목록 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public List<EnumGetter> getEnum(final String enumKey) {
    return get(enumKey).get(enumKey);
  }

  /**
   * 열거형 데이터 키 문자 목록 조회
   *
   * @param enumKey 열거형 데이터 키 문자 목록
   * @return 열거형 데이터 키 문자 목록
   * @apiNote 열거형 데이터 클래스의 키 문자 목록 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public List<String> getEnumKeys(final String enumKey) {
    List<EnumGetter> enumValues = getEnum(enumKey);
    List<String> keys = new ArrayList<>();

    for (EnumGetter enumValue : enumValues) {
      keys.add(enumValue.getKey());
    }

    return keys;
  }

  /**
   * 열거형 데이터 값 문자 목록 조회
   *
   * @param enumKey 열거형 데이터 값 문자 목록
   * @return 열거형 데이터 값 문자 목록
   * @apiNote 열거형 데이터 클래스의 값 문자 목록 조회
   * @author 류성재
   * @since 2022. 12. 23. 오후 4:23:59
   */
  public List<String> getEnumGetters(final String enumKey) {
    List<EnumGetter> enumValues = getEnum(enumKey);
    List<String> values = new ArrayList<>();

    for (EnumGetter enumValue : enumValues) {
      values.add(enumValue.getValue());
    }

    return values;
  }

}
