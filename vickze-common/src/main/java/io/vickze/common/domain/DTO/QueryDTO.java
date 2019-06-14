package io.vickze.common.domain.DTO;

import io.vickze.common.enums.DBOrder;
import io.vickze.common.util.SQLUtil;
import lombok.Data;

/**
 * 查询参数
 *
 * @author vick.zeng
 */
@Data
public class QueryDTO {

    private long offset = 0;

    private long limit = 10;

    //排序字段
    private String field;
    //排序方式 asc、desc
    private String order;

    public void setLimit(long limit) {
        //限制最大查询条数
        if (limit > 1000) {
            throw new RuntimeException();
        }
        this.limit = limit;
    }

    public void setField(String field) {
        //防止SQL注入
        SQLUtil.checkSqlInject(field);
        this.field = field;
    }

    public void setOrder(String order) {
        if (DBOrder.ASC.name().equalsIgnoreCase(order)) {
            this.order = DBOrder.ASC.name();
        } else {
            this.order = DBOrder.DESC.name();
        }

    }
}
