package io.vickze.auth.controller;

import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.domain.DO.MenuResourceDO;
import io.vickze.auth.domain.DO.RoleDO;
import io.vickze.auth.domain.DTO.AssignMenuResourceDTO;
import io.vickze.auth.domain.DTO.RoleQueryDTO;
import io.vickze.auth.service.RoleService;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:18:06
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDO> list(RoleQueryDTO queryDTO, HttpServletResponse response) {
        Pair<List<RoleDO>, Long> result = roleService.list(queryDTO);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/{id}")
    public RoleDO get(@PathVariable("id") Long id) {
        return roleService.get(id);
    }

    @PostMapping
    public void insert(@RequestBody @Validated(Insert.class) RoleDO role) {
        roleService.insert(role);
    }

    @PutMapping
    public void update(@RequestBody @Validated(Update.class) RoleDO role) {
        roleService.update(role);
    }

    @DeleteMapping
    public void delete(@RequestBody Long... ids) {
        roleService.delete(ids);
    }

    @PostMapping("/assignMenuResource")
    public void assignMenuResource(@RequestBody AssignMenuResourceDTO assignMenuResourceDTO) {
        roleService.assignMenuResource(assignMenuResourceDTO);
    }


    @GetMapping("/menuResource")
    public List<MenuResourceDO> getMenuResources(@RequestParam Long systemId, @RequestParam Long roleId) {
        return roleService.getMenuResources(systemId, roleId);
    }
}
