package io.vickze.generator.domain.DO;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 配置
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:04:44
 */
@Data
@Entity
@Table(name = "config")
@SQLDelete(sql = "update config set deleted = 1 where id = ? and version = ?")
@Where(clause = "deleted = 0")
@EntityListeners(AuditingEntityListener.class)
public class ConfigDO {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    private Long id;
    /**
     * 名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String name;
    /**
     * 内容
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String content;
    /**
     * 类型 0-yml 1-properties
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
    private Integer deleted;
    /**
     * 创建时间
     */
    @CreatedDate
    private LocalDateTime createTime;
    /**
     * 最后修改时间
     */
    @LastModifiedDate
    private LocalDateTime updateTime;
}
