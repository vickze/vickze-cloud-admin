package io.vickze;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TemplateTest {

    @Test
    public void propertiesTest() throws IOException {
        Properties properties = new Properties();
        String stringBuilder = "test1=111" + System.getProperty("line.separator") +
                "test2.1=2221" + System.getProperty("line.separator") +
                "test2.2=2222" + System.getProperty("line.separator") +
                "test3=333" + System.getProperty("line.separator") +
                "tes4=444" + System.getProperty("line.separator") +
                "test5=555" + System.getProperty("line.separator");
        properties.load(new StringReader(stringBuilder));
        System.out.println(properties);
    }

    @Test
    public void yamlTest() {
        Yaml yaml = new Yaml();
        String stringBuilder = "test1: 111" + System.getProperty("line.separator") +
                "test2: 222" + System.getProperty("line.separator") +
                "test3.1: 333" + System.getProperty("line.separator") +
                "test4: " + System.getProperty("line.separator") +
                "  test5: 445" + System.getProperty("line.separator");
        Map<String, Object> map = yaml.load(stringBuilder);
        System.out.println(map);
    }

    @Test
    public void velocityTest() {
        // 初始化并取得Velocity引擎
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        // 取得velocity的模版内容, 模板内容来自字符传
        String content = "";
        content += "Welcome  $name  to Javayou.com! ";
        content += " today is  $date.";
        content += " a is  $test.a";
        content += " b is  $test.b";
        content += " c is  \"$test.1\"";

        // 取得velocity的上下文context
        VelocityContext context = new VelocityContext();

        // 把数据填入上下文
        context.put("name", "javaboy2012");

        context.put("date", (new Date()).toString());

        Map<String, String> map = new HashMap<>();
        map.put("a", "a");
        map.put("b", "b");
        context.put("test", map);
        context.put("test.1", "c");

        // 输出流
        StringWriter writer = new StringWriter();

        // 转换输出

        ve.evaluate(context, writer, "", content); // 关键方法

        System.out.println(writer.toString());
    }
}
