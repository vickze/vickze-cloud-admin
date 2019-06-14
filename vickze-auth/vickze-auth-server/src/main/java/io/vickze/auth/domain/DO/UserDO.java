package io.vickze.auth.domain.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 用户
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:14:40
 */
@Data
@TableName("user")
public class UserDO {

    /**
     * 
     */
    @TableId(type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
}
