package io.vickze.auth.controller;

import io.vickze.auth.domain.DO.MenuDO;
import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DTO.MenuQueryDTO;
import io.vickze.auth.domain.DTO.MenuTreeDTO;
import io.vickze.auth.service.MenuService;
import io.vickze.auth.service.SystemService;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 15:16:39
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/tree")
    public List<MenuTreeDTO> tree(Long systemId) {
        return menuService.tree(systemId);
    }

    @GetMapping
    public List<MenuDO> list(MenuQueryDTO queryDTO, HttpServletResponse response) {
        Pair<List<MenuDO>, Long> result = menuService.list(queryDTO);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/{id}")
    public MenuDO get(@PathVariable("id") Long id) {
        return menuService.get(id);
    }

    @PostMapping
    public void insert(@RequestBody @Validated(Insert.class) MenuDO menuDO) {
        menuService.insert(menuDO);
    }

    @PutMapping
    public void update(@RequestBody @Validated(Update.class) MenuDO menuDO) {
        menuService.update(menuDO);
    }

    @DeleteMapping
    public void delete(@RequestBody Long... ids) {
        menuService.delete(ids);
    }
}
