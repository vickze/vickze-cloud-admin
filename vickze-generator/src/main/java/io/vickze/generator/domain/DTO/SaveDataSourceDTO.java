package io.vickze.generator.domain.DTO;

import lombok.Data;

@Data
public class SaveDataSourceDTO {

    private String ds;

    private String jdbcUrl;

    private String username;

    private String password;
}
