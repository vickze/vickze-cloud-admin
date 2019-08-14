package io.vickze.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-08-14 11:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interface {

    private String method;

    private String uri;
}
