package io.vickze.auth.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import io.vickze.auth.domain.DO.MenuResourceDO;
import io.vickze.auth.domain.DO.RoleDO;
import io.vickze.auth.domain.DTO.AssignMenuResourceDTO;
import io.vickze.auth.domain.DTO.RoleQueryDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * 角色
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:16:31
 */
public interface RoleService {

    Pair<List<RoleDO>, Long> list(RoleQueryDTO queryDTO);

    RoleDO get(Long id);

    void insert(RoleDO roleDO);

    void update(RoleDO roleDO);

    void delete(Long... ids);

    void assignMenuResource(AssignMenuResourceDTO assignMenuResourceDTO);

    List<MenuResourceDO> getMenuResources(String systemKey, Long... roleIds);

    List<RoleDO> selectByUserId(Long id);

    List<MenuResourceDO> getMenuResources(Long systemId, Long... roleIds);
}
