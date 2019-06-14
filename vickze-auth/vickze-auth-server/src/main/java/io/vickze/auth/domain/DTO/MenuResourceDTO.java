package io.vickze.auth.domain.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.vickze.auth.domain.DO.MenuResourceInterfaceDO;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-16 11:07
 */
@Data
public class MenuResourceDTO {


    /**
     *
     */
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
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;
    /**
     * 权限标识
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String permission;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;

    private List<MenuResourceInterfaceDO> interfaces;
}
