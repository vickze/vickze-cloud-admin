package io.vickze.auth.controller;

import io.vickze.auth.domain.DO.SystemDO;
import io.vickze.auth.domain.DTO.SystemQueryDTO;
import io.vickze.auth.service.SystemService;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 14:52:18
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping
    public List<SystemDO> list(SystemQueryDTO queryDTO, HttpServletResponse response) {
        Pair<List<SystemDO>, Long> result = systemService.list(queryDTO);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/{id}")
    public SystemDO get(@PathVariable("id") Long id) {
        return systemService.get(id);
    }

    @PostMapping
    public void insert(@RequestBody @Validated(Insert.class) SystemDO systemDO) {
        systemService.insert(systemDO);
    }

    @PutMapping
    public void update(@RequestBody @Validated(Update.class) SystemDO systemDO) {
        systemService.update(systemDO);
    }

    @DeleteMapping
    public void delete(@RequestBody Long... ids) {
        systemService.delete(ids);
    }
}
