package io.vickze.generator.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.vickze.generator.domain.DO.TemplateDO;
import io.vickze.generator.service.TemplateService;
import io.vickze.generator.mapper.TemplateMapper;
import io.vickze.common.domain.RPage;
import io.vickze.generator.domain.DTO.TemplateQueryDTO;
import io.vickze.common.enums.DBOrder;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 模版
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:24:22
 */
@Service
@DS("master")
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public Pair<List<TemplateDO>, Long> list(TemplateQueryDTO queryDTO) {
        IPage<TemplateDO> page = new RPage<>(queryDTO.getOffset(), queryDTO.getLimit());
        QueryWrapper<TemplateDO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(queryDTO.getName())) {
            wrapper = wrapper.like("name", queryDTO.getName());
        }
        if (CollectionUtils.isNotEmpty(queryDTO.getType())) {
            wrapper = wrapper.in("type", queryDTO.getType());
        }
        if (queryDTO.getField() != null) {
            wrapper = wrapper.orderBy(true, DBOrder.ASC.name().equals(queryDTO.getOrder()),
                    com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline(queryDTO.getField()));
        } else {
            wrapper = wrapper.orderByDesc("id");
        }
        templateMapper.selectPage(page, wrapper);
        return Pair.of(page.getRecords(), page.getTotal());
    }

    @Override
    public TemplateDO get(Long id) {
        return templateMapper.selectById(id);
    }

    @Override
    public void insert(TemplateDO templateDO) {
        templateMapper.insert(templateDO);
    }

    @Override
    public void update(TemplateDO templateDO) {
        templateMapper.updateById(templateDO);
    }

    @Override
    public void delete(Long... ids) {
        templateMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public Collection<TemplateDO> listByIds(List<Long> templateIds) {
        return templateMapper.selectBatchIds(templateIds);
    }
}
