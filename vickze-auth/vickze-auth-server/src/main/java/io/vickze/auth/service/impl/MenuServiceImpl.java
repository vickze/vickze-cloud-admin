package io.vickze.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.vickze.auth.domain.DO.MenuResourceDO;
import io.vickze.auth.domain.DO.MenuResourceInterfaceDO;
import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DTO.MenuTreeDTO;
import io.vickze.auth.mapper.MenuMapper;
import io.vickze.auth.mapper.MenuResourceInterfaceMapper;
import io.vickze.auth.mapper.MenuResourceMapper;
import io.vickze.auth.mapper.SystemMapper;
import io.vickze.auth.service.MenuResourceService;
import io.vickze.auth.service.SystemService;
import io.vickze.common.domain.RPage;
import io.vickze.auth.domain.DO.MenuDO;
import io.vickze.auth.domain.DTO.MenuQueryDTO;
import io.vickze.common.enums.DBOrder;
import io.vickze.auth.service.MenuService;
import java.util.ArrayList;
import javafx.util.Pair;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 15:16:39
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private MenuResourceMapper menuResourceMapper;
    @Autowired
    private MenuResourceInterfaceMapper menuResourceInterfaceMapper;

    @Override
    public List<MenuTreeDTO> tree(Long systemId) {
        QueryWrapper<MenuDO> wrapper = new QueryWrapper<>();
        wrapper.eq("system_id", systemId);
        wrapper.isNull("parent_id");
        List<MenuDO> menuDOList = menuMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(menuDOList)) {
            return new ArrayList<>();
        }

        return menuDOList.stream().map(menuDO -> {
            MenuTreeDTO menuTreeDTO = new MenuTreeDTO();
            BeanUtils.copyProperties(menuDO, menuTreeDTO);
            menuTreeDTO.setChildren(children(systemId, menuDO.getId()));
            return menuTreeDTO;
        }).collect(Collectors.toList());

    }



    @Override
    public Pair<List<MenuDO>, Long> list(MenuQueryDTO queryDTO) {
        IPage<MenuDO> page = new RPage<>(queryDTO.getOffset(), queryDTO.getLimit());
        QueryWrapper<MenuDO> wrapper = new QueryWrapper<>();
        if (queryDTO.getSystemId() != null) {
            wrapper = wrapper.eq("system_id", queryDTO.getSystemId());
        }
        if (queryDTO.getParentId() != null) {
            wrapper = wrapper.eq("parent_id", queryDTO.getParentId());
        }
        if (StringUtils.isNotBlank(queryDTO.getName())) {
            wrapper = wrapper.eq("name", queryDTO.getName());
        }
        if (queryDTO.getField() != null) {
            wrapper = wrapper.orderBy(true, DBOrder.ASC.name().equals(queryDTO.getOrder()),
                    com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(queryDTO.getField()));
        } else {
            wrapper = wrapper.orderByDesc("id");
        }
        menuMapper.selectPage(page, wrapper);
        return new Pair<>(page.getRecords(), page.getTotal());
    }

    @Override
    public MenuDO get(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public void insert(MenuDO menuDO) {
        menuMapper.insert(menuDO);
    }

    @Override
    public void update(MenuDO menuDO) {
        menuMapper.updateById(menuDO);
    }

    @Transactional
    @Override
    public void delete(Long... ids) {
        QueryWrapper<MenuResourceDO> menuResourceDOQueryWrapper = new QueryWrapper<>();
        menuResourceDOQueryWrapper.in("menu_id", ids);
        menuResourceMapper.delete(menuResourceDOQueryWrapper);

        QueryWrapper<MenuResourceInterfaceDO> menuResourceInterfaceDOQueryWrapper = new QueryWrapper<>();
        menuResourceInterfaceDOQueryWrapper.in("menu_id", ids);
        menuResourceInterfaceMapper.delete(menuResourceInterfaceDOQueryWrapper);

        menuMapper.deleteBatchIds(Arrays.asList(ids));
    }


    private List<MenuTreeDTO> children(Long systemId, Long parentId) {
        QueryWrapper<MenuDO> wrapper = new QueryWrapper<>();
        wrapper.eq("system_id", systemId);
        wrapper.eq("parent_id", parentId);
        List<MenuDO> menuDOList = menuMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(menuDOList)) {
            return null;
        }

        return menuDOList.stream().map(menuDO -> {
            MenuTreeDTO menuTreeDTO = new MenuTreeDTO();
            BeanUtils.copyProperties(menuDO, menuTreeDTO);
            menuTreeDTO.setChildren(children(systemId, menuDO.getId()));
            return menuTreeDTO;
        }).collect(Collectors.toList());
    }
}
