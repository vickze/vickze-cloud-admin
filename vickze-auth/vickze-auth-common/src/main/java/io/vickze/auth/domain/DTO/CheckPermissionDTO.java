package io.vickze.auth.domain.DTO;

import lombok.Data;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-03 11:32
 */
@Data
public class CheckPermissionDTO {

    private String requestUri;

    private String method;

    private String systemKey;

    private String token;

}
