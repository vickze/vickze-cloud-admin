package io.vickze.generator.service;

import io.vickze.generator.domain.DO.TableDO;
import io.vickze.generator.domain.DTO.GeneratorCodeDTO;
import io.vickze.generator.domain.DTO.GeneratorQueryDTO;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.configuration.ConfigurationException;

import java.io.IOException;
import java.util.List;

public interface GeneratorService {

    Pair<List<TableDO>, Long> tableList(GeneratorQueryDTO queryDTO);

    /**
     * 生成代码
     * @param generatorCode
     */
    byte[] generatorCode(GeneratorCodeDTO generatorCode);
}
