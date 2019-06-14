package io.vickze.auth.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

/**
 * 角色
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:15:36
 */
@Data
public class RoleQueryDTO extends QueryDTO {

    /**
     * 角色名称
     */
	private String name;
    /**
     * 备注
     */
	private String remark;
}
