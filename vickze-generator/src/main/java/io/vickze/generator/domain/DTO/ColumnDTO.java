package io.vickze.generator.domain.DTO;

import lombok.Data;

@Data
public class ColumnDTO {
    //列名
    private String name;
    //列名类型
    private String dataType;
    //
    private String characterMaximumLength;
    //列名备注
    private String comment;

    //属性名称(第一个字母大写)，如：user_name => UserName 大写驼峰
    private String attrNameUpperCamel;
    //属性名称(第一个字母小写)，如：user_name => userName 小写驼峰
    private String attrNameLowerCamel;
    //属性类型
    private String attrType;
    //auto_increment
    private String extra;

    private String isNullable;
}
