package io.vickze.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author vick.zeng
 * date-time: 2018/10/22 14:19
 **/
public class TwoBigDecimalSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        //使用writeNumber，小数点为.00序列化到前端会被清除，可以用spring注入的ObjectMapper writeString又存在，目前暂不清楚是哪里出了问题
        gen.writeNumber(value.setScale(2, RoundingMode.HALF_UP));
    }


}
