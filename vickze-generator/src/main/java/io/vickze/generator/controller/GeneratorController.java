package io.vickze.generator.controller;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.hikari.HikariCpConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.vickze.generator.domain.DO.TableDO;
import io.vickze.generator.domain.DTO.GeneratorCodeDTO;
import io.vickze.generator.domain.DTO.GeneratorQueryDTO;
import io.vickze.generator.domain.DTO.SaveDataSourceDTO;
import io.vickze.generator.domain.DTO.DataSourceDTO;
import io.vickze.generator.service.GeneratorService;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class GeneratorController {

    @Autowired //在需要的地方注入，然后调用相关方法增减数据源
    private DynamicRoutingDataSource dynamicRoutingDataSource;
    @Autowired
    private DynamicDataSourceProperties dynamicDataSourceProperties;

    @Autowired
    private GeneratorService generatorService;

    @GetMapping("/table")
    public List<TableDO> tableList(GeneratorQueryDTO query, HttpServletResponse response) {
        Pair<List<TableDO>, Long> result = generatorService.tableList(query);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/datasource/{ds}")
    public DataSourceDTO getDataSource(@PathVariable("ds") String ds) {
        DataSourceDTO dataSourceDTO = new DataSourceDTO();
        HikariDataSource dataSource = (HikariDataSource) dynamicRoutingDataSource.getCurrentDataSources().get(ds);
        if (dataSource == null) {
            return null;
        }
        dataSourceDTO.setJdbcUrl(dataSource.getJdbcUrl());
        return dataSourceDTO;
    }

    @PutMapping("/datasource")
    public void saveDataSource(@RequestBody SaveDataSourceDTO saveDataSource) {
        HikariCpConfig hikariCpConfig = dynamicDataSourceProperties.getDatasource().get(saveDataSource.getDs()) == null ?
                new HikariCpConfig() : dynamicDataSourceProperties.getDatasource().get(saveDataSource.getDs()).getHikari();

        HikariConfig config = hikariCpConfig.toHikariConfig(dynamicDataSourceProperties.getHikari());
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(saveDataSource.getJdbcUrl());
        config.setUsername(saveDataSource.getUsername());
        config.setPassword(saveDataSource.getPassword());

        dynamicRoutingDataSource.addDataSource(saveDataSource.getDs(), new HikariDataSource(config));
    }

    /**
     * 生成代码
     */
    @PostMapping("/code")
    public void code(@RequestBody GeneratorCodeDTO generatorCode, HttpServletResponse response) throws IOException, ConfigurationException {
        byte[] data = generatorService.generatorCode(generatorCode);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("代码生成", "UTF-8") + ".zip");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }

}
