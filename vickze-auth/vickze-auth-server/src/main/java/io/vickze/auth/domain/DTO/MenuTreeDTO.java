package io.vickze.auth.domain.DTO;


import lombok.Data;

import java.util.List;

@Data
public class MenuTreeDTO {

    /**
     *
     */
    private Long id;
    /**
     * 子菜单列表
     */
    private List<MenuTreeDTO> children;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;

}
