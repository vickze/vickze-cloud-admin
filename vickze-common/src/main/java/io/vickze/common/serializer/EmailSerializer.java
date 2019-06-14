package io.vickze.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EmailSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            String result = "";
            String[] split = s.split("@");
            String s1 = split[0];
            String s2 = split[1];
            if (s == null) {
                jsonGenerator.writeNull();
                return;
            } else if (s1.length() <= 4) {
                if (s1.length() <= 2) {
                    result = s1;
                } else {
                    result = s1.substring(0, 2) + s1.substring(2).replaceAll(".", "*");
                }
            } else {
                result = s1.substring(0, 4) + s1.substring(4).replaceAll(".", "*");
            }
            jsonGenerator.writeString(result + "@" + s2);
        } catch (Exception e) {
            jsonGenerator.writeString(s);
        }
    }
}
