package io.vickze.auth.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

/**
 * 菜单
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-05-15 15:16:39
 */
@Data
public class MenuQueryDTO extends QueryDTO {

    /**
     * 系统ID
     */
	private Long systemId;
    /**
     * 父ID
     */
	private Long parentId;
    /**
     * 名称
     */
	private String name;
}
