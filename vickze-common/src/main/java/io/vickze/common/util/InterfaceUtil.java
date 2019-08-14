package io.vickze.common.util;

import io.vickze.common.domain.Interface;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-07-17 15:25
 */
public class InterfaceUtil {

    /**
     * 正则匹配，requestUri是否匹配uri，{}自动替换，如uri为 /auth/user/{id} 替换为/auth/user/\{[a-zA-Z\d]+\}
     *
     * @param requestUri 请求uri
     * @param uri        匹配uri
     * @return
     */
    public static boolean uriMatch(String requestUri, String uri) {
        //去掉最后的/
        if (requestUri.endsWith("/")) {
            requestUri = requestUri.substring(0, requestUri.lastIndexOf("/"));
        }
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.lastIndexOf("/"));
        }

        if (requestUri.equals(uri)) {
            return true;
        }

        String regEx = uri;

        if (regEx.indexOf("{") > 0) {
            //替换{...} 英文数字
            regEx = regEx.replaceAll("\\{[a-zA-Z\\d]+\\}", "[a-zA-Z\\\\d]+");
        }

        if (Pattern.compile(regEx).matcher(requestUri).find()) {
            return true;
        }
        return false;
    }

    public static boolean interfaceMatch(Interface requestInterface, Interface _interface) {
        if (!StringUtils.equals(requestInterface.getMethod(), _interface.getMethod())) {
            return false;
        }
        return uriMatch(requestInterface.getUri(), _interface.getUri());
    }

    public static boolean interfaceContains(Interface requestInterface, List<Interface> _interfaces) {
        for (Interface _interface : _interfaces) {
            if (interfaceMatch(requestInterface, _interface)) {
                return true;
            }
        }

        return false;
    }
}
