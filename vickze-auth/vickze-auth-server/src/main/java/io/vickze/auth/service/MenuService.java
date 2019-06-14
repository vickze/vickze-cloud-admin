package io.vickze.auth.service;

import io.vickze.auth.domain.DO.MenuDO;
import io.vickze.auth.domain.DTO.MenuQueryDTO;
import io.vickze.auth.domain.DTO.MenuTreeDTO;
import javafx.util.Pair;

import java.util.List;

/**
 * 菜单
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 15:16:39
 */
public interface MenuService {

    List<MenuTreeDTO> tree(Long systemId);

    Pair<List<MenuDO>, Long> list(MenuQueryDTO queryDTO);

    MenuDO get(Long id);

    void insert(MenuDO menuDO);

    void update(MenuDO menuDO);

    void delete(Long... ids);
}
