package io.vickze.auth.controller;

import io.vickze.auth.client.UserClient;
import io.vickze.auth.constant.GlobalConstant;
import io.vickze.auth.domain.DTO.*;
import io.vickze.auth.resovler.AuthUser;
import io.vickze.auth.service.UserService;
import io.vickze.common.validation.Insert;
import io.vickze.common.validation.Update;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户
 *
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-04-16 11:18:06
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController implements UserClient {

    @Autowired
    private UserService userService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/test")
    public void test() {
        log.debug(serverPort);
    }

    @GetMapping
    public List<UserDTO> list(UserQueryDTO queryDTO, HttpServletResponse response) {
        Pair<List<UserDTO>, Long> result = userService.list(queryDTO);
        response.addHeader("X-Total-Count", String.valueOf(result.getValue()));
        return result.getKey();
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable("id") Long id) {
        return userService.get(id);
    }

    @PostMapping
    public void insert(@RequestBody @Validated(Insert.class) UserDTO user) {
        userService.insert(user);
    }

    @PutMapping
    public void update(@RequestBody @Validated(Update.class) UserDTO user) {
        userService.update(user);
    }

    @DeleteMapping
    public void delete(@RequestBody Long... ids) {
        userService.delete(ids);
    }

    @PostMapping("/token")
    public TokenDTO create(@RequestHeader(GlobalConstant.SYSTEM_HEADER) String systemKey, @RequestBody CreateTokenDTO createTokenDTO) {
        createTokenDTO.setSystemKey(systemKey);
        return userService.createToken(createTokenDTO);
    }

    @GetMapping("/_current") // _ 字符防止获取接口权限的时候被 /user/{id}匹配到，另外也可以配置忽略该接口权限
    public CurrentUserDTO current(@AuthUser AuthUserDTO authUserDTO,
                                  @RequestHeader(GlobalConstant.SYSTEM_HEADER) String systemKey) {
        CurrentUserDTO currentUserDTO = new CurrentUserDTO();
        BeanUtils.copyProperties(authUserDTO, currentUserDTO);
        currentUserDTO.setPermissions(userService.getMenuPermissions(systemKey, currentUserDTO.getUserId()));
        return currentUserDTO;
    }

    @Override
    public void checkSystemAccess(String systemKey, Long userId) {
        userService.checkSystemAccess(systemKey, userId);
    }
}
