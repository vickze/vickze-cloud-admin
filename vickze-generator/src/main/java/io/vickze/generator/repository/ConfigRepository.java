package io.vickze.generator.repository;

import io.vickze.generator.domain.DO.ConfigDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 配置
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:04:44
 */
@Repository
public interface ConfigRepository extends JpaRepository<ConfigDO, Long>,
        JpaSpecificationExecutor<ConfigDO> {
}
