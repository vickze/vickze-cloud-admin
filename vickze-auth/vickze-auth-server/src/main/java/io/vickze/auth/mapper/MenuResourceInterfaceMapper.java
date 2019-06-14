package io.vickze.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.auth.domain.DO.MenuResourceInterfaceDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单资源与接口对应关系
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-16 11:09:46
 */
@Mapper
public interface MenuResourceInterfaceMapper extends BaseMapper<MenuResourceInterfaceDO> {
}
