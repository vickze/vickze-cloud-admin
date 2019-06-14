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
 * 菜单资源与接口对应关系
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-16 11:09:46
 */
@Data
@TableName("menu_resource_interface")
public class MenuResourceInterfaceDO {

    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @NotNull(groups = Update.class)
	private Long id;
    /**
     * 系统ID
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Long systemId;
    /**
     * 菜单ID
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Long menuId;
    /**
     * 菜单资源ID
     */
    @NotNull(groups = {Insert.class, Update.class})
	private Long menuResourceId;
    /**
     * 接口uri
     */
    @NotBlank(groups = {Insert.class, Update.class})
	private String interfaceUri;
    /**
     * 接口方法
     */
    @NotBlank(groups = {Insert.class, Update.class})
	private String interfaceMethod;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
}
