package io.vickze.common.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vick.zeng
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Excel {

    String name() default "";

    String replace() default "";

    String format() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 忽略导出需要而导入不需要的字段
     * @return
     */
    boolean importIgnore() default false;

    /**
     * 导入字段可否为空
     * @return
     */
    boolean importNullable() default true;
}
