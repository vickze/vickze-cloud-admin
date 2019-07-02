package io.vickze.excel;

import io.vickze.common.excel.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MsgClient {

    @Excel(name = "生日")
    private Date birthday;

    @Excel(name = "姓名")
    private String clientName;

    @Excel(name = "手机号")
    private String clientPhone;

    @Excel(name = "编号")
    private String id;

    @Excel(name = "创建人")
    private String createBy;

    @Excel(name = "备注")
    private String remark;
}
