package io.vickze.auth.domain.DTO;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.vickze.auth.domain.DO.RoleDO;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-27 14:38
 */
@Data
public class UserDTO {
    /**
     *
     */
    @NotNull(groups = Update.class)
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String username;
    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(groups = {Insert.class, Update.class})
    @ApiModelProperty
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户角色列表
     */
    private List<RoleDO> roles;

}
