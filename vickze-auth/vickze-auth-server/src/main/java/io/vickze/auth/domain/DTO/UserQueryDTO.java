package io.vickze.auth.domain.DTO;

import io.vickze.common.domain.DTO.QueryDTO;
import lombok.Data;

import java.util.List;

/**
 * 用户
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:15:36
 */
@Data
public class UserQueryDTO extends QueryDTO {

    /**
     * 用户名
     */
	private String username;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 手机号
     */
	private String mobile;
    /**
     * 状态  0：禁用   1：正常
     */
	private List<Integer> status;
}
