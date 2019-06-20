package io.vickze.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.vickze.auth.domain.DO.MenuResourceDO;
import io.vickze.auth.domain.DTO.MenuResourceDTO;
import io.vickze.auth.domain.DTO.MenuResourceQueryDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * 菜单资源
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-29 15:01:06
 */
public interface MenuResourceService {

    Pair<List<MenuResourceDTO>, Long> list(MenuResourceQueryDTO queryDTO);

    MenuResourceDTO get(Long id);

    void insert(MenuResourceDTO menuResourceDTO);

    void update(MenuResourceDTO menuResourceDTO);

    void delete(Long... ids);

    List<String> getPermissions(String systemKey, String uri, String method);

    List<String> getPermissions(Long systemId, String uri, String method);

    List<MenuResourceDO> selectList(QueryWrapper<MenuResourceDO> menuResourceDOQueryWrapper);
}
