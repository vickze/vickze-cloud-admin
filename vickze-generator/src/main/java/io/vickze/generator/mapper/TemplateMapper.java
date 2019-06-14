package io.vickze.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.generator.domain.DO.TemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 模版
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:24:22
 */
@Mapper
public interface TemplateMapper extends BaseMapper<TemplateDO> {
}
