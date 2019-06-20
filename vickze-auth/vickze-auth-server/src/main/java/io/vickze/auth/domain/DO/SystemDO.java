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
 * 系统
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 14:49:10
 */
@Data
@TableName("`system`")
public class SystemDO {

    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @NotNull(groups = Update.class)
	private Long id;
    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
	private String name;
    /**
     * Key
     */
    @NotBlank(groups = {Insert.class, Update.class})
    @TableField("`key`")
	private String key;

    /**
     * 是否允许无菜单资源权限登录
     */
    private Boolean notResourceLogin;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}
