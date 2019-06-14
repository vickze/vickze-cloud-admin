package io.vickze.auth.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

/**
 * 系统
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 14:49:10
 */
@Data
public class SystemQueryDTO extends QueryDTO {

    /**
     * 名称
     */
	private String name;
    /**
     * Key
     */
	private String key;
}
