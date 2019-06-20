package io.vickze.auth.service;

import io.vickze.auth.domain.DO.UserDO;
import io.vickze.auth.domain.DTO.CheckPermissionDTO;
import io.vickze.auth.domain.DTO.UserDTO;
import io.vickze.auth.domain.DTO.UserQueryDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;

/**
 * 用户
 * 
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:16:31
 */
public interface UserService {

    Pair<List<UserDTO>, Long> list(UserQueryDTO queryDTO);

    UserDTO get(Long id);

    void insert(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(Long... ids);

    UserDO getByUsername(String username);

    UserDO validate(String username, String password);

    Set<String> getMenuPermissions(String systemKey, Long userId);

    Set<String> getMenuPermissions(Long systemId, Long userId);
}
