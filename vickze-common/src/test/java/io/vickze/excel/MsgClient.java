package io.vickze.excel;

import io.vickze.common.excel.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class MsgClient {

    @Excel(name = "birthday")
    private Date birthday;

    @Excel(name = "clientName")
    private String clientName;

    private String clientPhone;

    private String id;

    private String createBy;

    private String remark;
}
