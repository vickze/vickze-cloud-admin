package io.vickze.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * SQL过滤
 *
 * @author vick.zeng
 */
@Slf4j
public class SQLUtil {

    /**
     * 正则表达式
     **/
    private static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    //\\b  表示 限定单词边界  比如  select 不通过   1select则是可以的
    private static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

    /**
     * SQL注入检查
     *
     * @param str 待验证的字符串
     */
    public static void checkSqlInject(String str) {
        if (sqlPattern.matcher(str).find())
        {
            log.error("存在sql注入风险：str={}", str);
            throw new RuntimeException("包含非法字符");
        }
    }
}
