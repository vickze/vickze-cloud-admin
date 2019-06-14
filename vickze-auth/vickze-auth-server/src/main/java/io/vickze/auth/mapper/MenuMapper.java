package io.vickze.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.vickze.auth.domain.DO.MenuDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 15:16:39
 */
@Mapper
public interface MenuMapper extends BaseMapper<MenuDO> {
}
