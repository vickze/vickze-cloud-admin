package io.vickze.common.domain.DTO;

import lombok.Data;

/**
 * 返回数据（用于处理Http状态码不能够表示的情况）
 *
 * @author vick.zeng
 */
@Data
public class BusinessResultDTO {
	public static final String SUCCESS = "0";
	/**
	 * 业务状态码
	 */
	private String code;

	private String msg;

	private Object data;

}
