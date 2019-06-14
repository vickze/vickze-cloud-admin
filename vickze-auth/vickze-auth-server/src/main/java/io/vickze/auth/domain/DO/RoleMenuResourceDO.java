package io.vickze.auth.domain.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 角色与菜单资源对应关系
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-30 15:32:48
 */
@Data
@TableName("role_menu_resource")
public class RoleMenuResourceDO {

    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @NotNull(groups = Update.class)
	private Long id;
    /**
     * 角色ID
     */
	private Long roleId;
    /**
     * 角色ID
     */
    private Long systemId;
    /**
     * 菜单资源ID
     */
	private Long menuResourceId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
}
