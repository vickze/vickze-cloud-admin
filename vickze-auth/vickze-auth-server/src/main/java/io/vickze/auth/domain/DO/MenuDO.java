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
 * 菜单
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 15:35:39
 */
@Data
@TableName("menu")
public class MenuDO {

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
     * 父ID
     */
	private Long parentId;
    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
	private String name;
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
