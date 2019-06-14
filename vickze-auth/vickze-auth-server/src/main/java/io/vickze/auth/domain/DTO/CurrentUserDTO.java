package io.vickze.auth.domain.DTO;

import lombok.Data;

import java.util.Set;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-06-03 15:20
 */
@Data
public class CurrentUserDTO {


    private Long userId;

    private String username;

    private Set<String> permissions;
}
