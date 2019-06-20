package io.vickze.generator.service;

import io.vickze.generator.domain.DO.ConfigDO;
import io.vickze.generator.domain.DTO.ConfigQueryDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 配置
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:04:44
 */
public interface ConfigService {

    Pair<List<ConfigDO>, Long> list(ConfigQueryDTO queryDTO);

    ConfigDO get(Long id);

    void insert(ConfigDO configDO);

    void update(ConfigDO configDO);

    void delete(Long... ids);
}
