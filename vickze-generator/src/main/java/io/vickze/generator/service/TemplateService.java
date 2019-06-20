package io.vickze.generator.service;

import io.vickze.generator.domain.DO.TemplateDO;
import io.vickze.generator.domain.DTO.TemplateQueryDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.List;

/**
 * 模版
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:24:22
 */
public interface TemplateService {

    Pair<List<TemplateDO>, Long> list(TemplateQueryDTO queryDTO);

    TemplateDO get(Long id);

    void insert(TemplateDO templateDO);

    void update(TemplateDO templateDO);

    void delete(Long... ids);

    Collection<TemplateDO> listByIds(List<Long> templateIds);
}
