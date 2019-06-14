package io.vickze.auth.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AssignMenuResourceDTO {

    private Long roleId;

    private Long systemId;

    private List<Long> menuResourceIds;
}
