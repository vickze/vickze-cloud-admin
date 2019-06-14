package io.vickze.generator.controller;

import io.vickze.generator.domain.DO.TemplateDO;
import io.vickze.generator.domain.DTO.TemplateQueryDTO;
import io.vickze.generator.service.TemplateService;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 模版
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:24:22
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @GetMapping
    public List<TemplateDO> list(TemplateQueryDTO queryDTO, HttpServletResponse response) {
        Pair<List<TemplateDO>, Long> result = templateService.list(queryDTO);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/{id}")
    public TemplateDO get(@PathVariable("id") Long id) {
        return templateService.get(id);
    }

    @PostMapping
    public void insert(@RequestBody @Validated(Insert.class) TemplateDO template) {
        templateService.insert(template);
    }

    @PutMapping
    public void update(@RequestBody @Validated(Update.class) TemplateDO template) {
        templateService.update(template);
    }

    @DeleteMapping
    public void delete(@RequestBody Long... ids) {
        templateService.delete(ids);
    }
}
