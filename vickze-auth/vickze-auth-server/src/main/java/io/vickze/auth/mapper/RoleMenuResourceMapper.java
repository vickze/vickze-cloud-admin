package io.vickze.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.auth.domain.DO.MenuResourceDO;
import io.vickze.auth.domain.DO.RoleMenuResourceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色与菜单资源对应关系
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-30 15:32:48
 */
@Mapper
public interface RoleMenuResourceMapper extends BaseMapper<RoleMenuResourceDO> {
    List<MenuResourceDO> getPermissionByRoleId(Long id);
}
