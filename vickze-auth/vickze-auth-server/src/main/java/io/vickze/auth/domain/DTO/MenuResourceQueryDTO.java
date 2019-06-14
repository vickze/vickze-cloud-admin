package io.vickze.auth.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

/**
 * 菜单资源
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-29 15:01:06
 */
@Data
public class MenuResourceQueryDTO extends QueryDTO {

    /**
     * 菜单唯一标识
     */
	private String menuId;

}
