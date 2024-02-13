package run.freshr.domain.auth.repository.redis;

import run.freshr.domain.auth.redis.RsaPair;
import org.springframework.data.repository.CrudRepository;

/**
 * RSA 키 관리 repository
 *
 * @author 류성재
 * @apiNote RSA 키 관리 repository
 * @since 2023. 6. 15. 오후 5:10:05
 */
public interface RsaPairRepository extends CrudRepository<RsaPair, String> {

}
