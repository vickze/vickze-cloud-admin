package io.vickze.generator.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TableDTO {
    //表的名称
    private String name;
    //表的备注
    private String comments;
    //表的主键
    private ColumnDTO pk;
    //表的列名(不包含主键)
    private List<ColumnDTO> columns;

    //类名(第一个字母大写)，如：sys_user => SysUser 大写驼峰
    private String classNameUpperCamel;
    //类名(第一个字母小写)，如：sys_user => sysUser 小写驼峰
    private String classNameLowerCamel;
}
