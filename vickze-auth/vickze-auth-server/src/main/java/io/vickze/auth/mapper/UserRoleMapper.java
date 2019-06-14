package io.vickze.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.auth.domain.DO.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户与角色对应关系
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:19:31
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
}
