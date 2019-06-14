package io.vickze.generator.domain.DO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("information_schema.columns")
@Data
public class ColumnDO {

    private String columnName;

    private String dataType;

    private String columnComment;

    private String columnKey;

    private String extra;

    private String isNullable;
}
