package io.vickze.auth.controller;

import io.vickze.auth.domain.DTO.MenuResourceDTO;
import io.vickze.auth.domain.DTO.MenuResourceQueryDTO;
import io.vickze.auth.service.MenuResourceService;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单资源
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-29 15:01:06
 */
@RestController
@RequestMapping("/menuResource")
public class MenuResourceController {

    @Autowired
    private MenuResourceService menuResourceService;

    @GetMapping
    public List<MenuResourceDTO> list(MenuResourceQueryDTO queryDTO, HttpServletResponse response) {
        Pair<List<MenuResourceDTO>, Long> result = menuResourceService.list(queryDTO);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/{id}")
    public MenuResourceDTO get(@PathVariable("id") Long id) {
        return menuResourceService.get(id);
    }

    @PostMapping
    public void insert(@RequestBody @Validated(Insert.class) MenuResourceDTO menuResourceDTO) {
        menuResourceService.insert(menuResourceDTO);
    }

    @PutMapping
    public void update(@RequestBody @Validated(Update.class) MenuResourceDTO menuResourceDTO) {
        menuResourceService.update(menuResourceDTO);
    }

    @DeleteMapping
    public void delete(@RequestBody Long... ids) {
        menuResourceService.delete(ids);
    }
}
