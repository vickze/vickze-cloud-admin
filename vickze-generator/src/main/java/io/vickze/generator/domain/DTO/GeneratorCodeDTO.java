package io.vickze.generator.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GeneratorCodeDTO {

    private Long configId;

    private List<Long> templateIds;

    private List<String> tableNames;
}
