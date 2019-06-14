package io.vickze.generator.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

@Data
public class GeneratorQueryDTO extends QueryDTO {

    private String tableName;
}
