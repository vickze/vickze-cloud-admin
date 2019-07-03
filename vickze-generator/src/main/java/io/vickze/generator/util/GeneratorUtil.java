package io.vickze.generator.util;


import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * 代码生成器工具类
 *
 * @author vick.zeng
 * @email 2512522383@qq.com
 * @create 2017-09-08 21:56
 */
public class GeneratorUtil {
    /**
     * 表名转换权限字符串
     */
    public static String tableToPermission(String tableName) {
        return StringUtils.replace(tableName, "_", ":");
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(VelocityContext velocityContext, String template, String className, String classname, String packageName) {
        // 输出流
        StringWriter writer = new StringWriter();

        // 转换输出
        Velocity.evaluate(velocityContext, writer, "", template); // 关键方法

        return writer.toString();
    }

    /**
     * 获取文件名
     */
    public static String getFileName(freemarker.template.Configuration configuration, String fileName, Map<String, Object> config) throws IOException, TemplateException {
        // 输出流
        StringWriter writer = new StringWriter();

        // 转换输出
        new Template("template", new StringReader(fileName), configuration).process(config, writer); // 关键方法


        return writer.toString();
    }
}
