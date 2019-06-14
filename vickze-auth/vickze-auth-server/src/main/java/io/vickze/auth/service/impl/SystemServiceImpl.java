package io.vickze.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.vickze.auth.mapper.SystemMapper;
import io.vickze.common.domain.RPage;
import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DTO.SystemQueryDTO;
import io.vickze.common.enums.DBOrder;
import io.vickze.auth.service.SystemService;
import java.util.ArrayList;
import javafx.util.Pair;
import org.springframework.beans.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 系统
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 14:52:47
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SystemMapper systemMapper;

    @Override
    public Pair<List<SystemDO>, Long> list(SystemQueryDTO queryDTO) {
        IPage<SystemDO> page = new RPage<>(queryDTO.getOffset(), queryDTO.getLimit());
        QueryWrapper<SystemDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(queryDTO.getName())) {
            wrapper = wrapper.eq("name", queryDTO.getName());
        }
        if (StringUtils.isNotBlank(queryDTO.getKey())) {
            wrapper = wrapper.eq("key", queryDTO.getKey());
        }
        if (queryDTO.getField() != null) {
            wrapper = wrapper.orderBy(true, DBOrder.ASC.name().equals(queryDTO.getOrder()),
                    com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(queryDTO.getField()));
        } else {
            wrapper = wrapper.orderByDesc("id");
        }
        systemMapper.selectPage(page, wrapper);
        return new Pair<>(page.getRecords(), page.getTotal());
    }

    @Override
    public SystemDO get(Long id) {
        return systemMapper.selectById(id);
    }

    @Override
    public void insert(SystemDO systemDO) {
        systemMapper.insert(systemDO);
    }

    @Override
    public void update(SystemDO systemDO) {
        systemMapper.updateById(systemDO);
    }

    @Override
    public void delete(Long... ids) {
        systemMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public SystemDO selectByKey(String systemKey) {
        QueryWrapper<SystemDO> wrapper = new QueryWrapper<>();
        wrapper.eq("`key`", systemKey);
        return systemMapper.selectOne(wrapper);
    }
}
