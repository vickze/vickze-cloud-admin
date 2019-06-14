package io.vickze.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.generator.domain.DO.TableDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableMapper extends BaseMapper<TableDO> {
}
