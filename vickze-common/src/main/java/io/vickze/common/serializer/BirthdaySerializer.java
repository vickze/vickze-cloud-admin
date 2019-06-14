package io.vickze.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BirthdaySerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date d, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (d == null) {
            jsonGenerator.writeNull();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM ****", Locale.ENGLISH);
        jsonGenerator.writeString(dateFormat.format(d));
    }
}
