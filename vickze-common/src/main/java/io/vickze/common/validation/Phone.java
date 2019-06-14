package io.vickze.common.validation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * Created by vick.zeng on 2017-06-30.
 */
@Documented
@Constraint(
        validatedBy = {}
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@Pattern(
        regexp = ""
)
public @interface Phone {
    String message() default "手机号码格式错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @OverridesAttribute(
            constraint = Pattern.class,
            name = "regexp"
    )

    /**
     * 中国大陆手机号格式校验正则
     * 包括电信、联通、移动、虚拟号码段
     * 中国移动号段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,147,178
     * 中国联通号段：130,131,132,155,156,185,186,145,176,175
     * 中国电信号段：133,153,180,181,189,177,173
     * 中国虚拟运营商号段：170,171
     * 总计38个号段
     *
     * 2017/08/08 新增号段：电信199/ 移动198/ 联通166，另外还有146联通，148移动
     * 总计43个号段
     */
    String regexp() default "(?:^(?:\\+86)?1(?:3[0-9]|4[5-8]|5[0-35-9]|6[6]|7[0135-8]|8[0-9]|9[89])\\d{8}$)";

    @OverridesAttribute(
            constraint = Pattern.class,
            name = "flags"
    )
    Pattern.Flag[] flags() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        Phone[] value();
    }
}
