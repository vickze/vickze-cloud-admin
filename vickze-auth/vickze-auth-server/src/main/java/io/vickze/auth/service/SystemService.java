package io.vickze.auth.service;

import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DTO.SystemQueryDTO;
import javafx.util.Pair;

import java.util.List;

/**
 * 系统
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-14 11:24:11
 */
public interface SystemService {

    Pair<List<SystemDO>, Long> list(SystemQueryDTO queryDTO);

    SystemDO get(Long id);

    void insert(SystemDO systemDO);

    void update(SystemDO systemDO);

    void delete(Long... ids);

    SystemDO selectByKey(String systemKey);
}
