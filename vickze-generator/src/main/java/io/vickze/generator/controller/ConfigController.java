package io.vickze.generator.controller;

import io.vickze.auth.domain.DTO.AuthUserDTO;
import io.vickze.auth.resovler.AuthUser;
import io.vickze.generator.domain.DO.ConfigDO;
import io.vickze.generator.domain.DTO.ConfigQueryDTO;
import io.vickze.generator.service.ConfigService;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import org.apache.commons.lang3.tuple.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 配置
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:04:44
 */
@Slf4j
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @ResponseBody
    @GetMapping
    public List<ConfigDO> list(ConfigQueryDTO queryDTO, HttpServletResponse response, @AuthUser AuthUserDTO authUserDTO) {
        Pair<List<ConfigDO>, Long> result = configService.list(queryDTO);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/{id}")
    public ConfigDO get(@PathVariable("id") Long id) {
        return configService.get(id);
    }

    @PostMapping
    public void insert(@RequestBody @Validated(Insert.class) ConfigDO config) {
        configService.insert(config);
    }

    @PutMapping
    public void update(@RequestBody @Validated(Update.class) ConfigDO config, @AuthUser AuthUserDTO authUserDTO) {
        log.info("操作用户：{}-{}", authUserDTO.getUserId(), authUserDTO.getUsername());
        configService.update(config);
    }

    @DeleteMapping
    public void delete(@RequestBody Long... ids) {
        configService.delete(ids);
    }
}
