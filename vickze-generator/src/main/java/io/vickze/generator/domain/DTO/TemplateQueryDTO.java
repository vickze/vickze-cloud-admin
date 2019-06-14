package io.vickze.generator.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

import java.util.List;

@Data
public class TemplateQueryDTO extends QueryDTO {

    private String name;

    private List<Integer> type;
}
