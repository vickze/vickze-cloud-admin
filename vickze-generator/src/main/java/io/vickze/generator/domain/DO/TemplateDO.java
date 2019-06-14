package io.vickze.generator.domain.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 模版
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:23:41
 */
@Data
@TableName("template")
public class TemplateDO {

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
     * 文件名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String fileName;
    /**
     * 内容
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String content;
    /**
     * 类型 0-Velocity 1-Freemarker
     */
    @NotNull(groups = {Insert.class, Update.class})
    private Integer type;
    /**
     * 版本号
     */
    @Version
    private Integer version;
    /**
     * 删除标志
     */
    @JsonIgnore
    @TableLogic
    private Integer deleted;
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
