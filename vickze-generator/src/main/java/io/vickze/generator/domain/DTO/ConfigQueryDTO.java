package io.vickze.generator.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

import java.util.List;

/**
 * 配置
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-03-29 16:04:44
 */
@Data
public class ConfigQueryDTO extends QueryDTO {

    private String name;

    private List<Integer> type;
}
