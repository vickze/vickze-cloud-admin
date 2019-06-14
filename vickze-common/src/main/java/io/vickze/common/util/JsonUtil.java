package io.vickze.common.util;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import io.vickze.common.deserializer.ZonedDateTimeDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Json处理工具类
 *
 * @author vick.zeng
 **/
@Slf4j
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();


    static {
        //reference from Jackson2ObjectMapperBuilder line 730
        Class<? extends Module> javaTimeModuleClass = null;
        try {
            javaTimeModuleClass = (Class<? extends Module>)
                    ClassUtils.forName("com.fasterxml.jackson.datatype.jsr310.JavaTimeModule", JsonUtil.class.getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        JavaTimeModule javaTimeModule = (JavaTimeModule) BeanUtils.instantiateClass(javaTimeModuleClass);
        //ZonedDateTime序列化、反序列化
        javaTimeModule.addSerializer(ZonedDateTime.class,
                new ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        javaTimeModule.addDeserializer(ZonedDateTime.class, ZonedDateTimeDeserializer.INSTANCE);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(javaTimeModule);
    }

    public static <T> String toJson(T data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error("To json failure, data:{}", data);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            if (json == null) {
                return null;
            }
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("From json failure, json:{}", json);
            throw new RuntimeException(e);
        }
    }


    public static <T> T fromJson(String json, TypeReference<T> jsonTypeReference) {
        try {
            if (json == null) {
                return null;
            }
            return mapper.readValue(json, jsonTypeReference);
        } catch (Exception e) {
            log.error("From json failure, json:{}", json);
            throw new RuntimeException(e);
        }
    }

    public static<T> T fromJson(String json, Class<T> collectionClass, Class<?>... elementClasses) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return mapper.readValue(json, javaType);
        } catch (Exception e) {
            log.error("From json failure, json:{}", json);
            return null;
        }
    }
}
