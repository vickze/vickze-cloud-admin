package io.vickze.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.generator.domain.DO.ColumnDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ColumnMapper extends BaseMapper<ColumnDO> {
}
