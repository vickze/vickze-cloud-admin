package io.vickze.auth.util;

import java.util.regex.Pattern;

/**
 * @author vick.zeng
 * @email zyk@yk95.top
 * @create 2019-07-17 15:25
 */
public class MatchUtil {

    public static boolean uriMatch(String requestUri, String interfaceUri) {
        //去掉最后的/
        if (requestUri.endsWith("/")) {
            requestUri = requestUri.substring(0, requestUri.lastIndexOf("/"));
        }
        if (interfaceUri.endsWith("/")) {
            interfaceUri = interfaceUri.substring(0, interfaceUri.lastIndexOf("/"));
        }

        if (requestUri.equals(interfaceUri)) {
            return true;
        }

        String regEx = interfaceUri;

        if (regEx.indexOf("{") > 0) {
            //替换{...} 英文数字
            regEx = regEx.replaceAll("\\{[a-zA-Z\\d]+\\}", "[a-zA-Z\\\\d]+");
        }
        if (regEx.indexOf("*") > 0) {
            regEx = regEx.replace("*", ".*");
        }

        if (Pattern.compile(regEx).matcher(requestUri).find()) {
            return true;
        }
        return false;
    }
}
