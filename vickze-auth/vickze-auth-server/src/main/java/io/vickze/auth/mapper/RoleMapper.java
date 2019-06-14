package io.vickze.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.auth.domain.DO.RoleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:16:31
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {
}
