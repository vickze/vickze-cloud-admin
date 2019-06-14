package io.vickze.generator.domain.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@TableName("information_schema.tables")
@Data
public class TableDO {

    private String tableName;

    private String tableComment;

    private String engine;

    private LocalDateTime createTime;
}
