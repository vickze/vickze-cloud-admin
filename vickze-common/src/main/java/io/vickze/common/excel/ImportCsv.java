package io.vickze.common.excel;

import io.vickze.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class ImportCsv {


    public <T> List<T> importCsvZip(InputStream inputStream, Class<T> clazz) {
        try {
            List<T> result = new LinkedList<>();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                log.debug("导入zip-{}", zipEntry.getName());
                result.addAll(importCsv(zipInputStream, clazz));
            }
            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("导入CSV ZIP失败");
        }
    }

    public <T> List<T> importCsv(InputStream inputStream, Class<T> clazz) {
        try {
            LinkedHashMap<Field, Method> writeFieldMethod = writeMethod(clazz);
            List<T> results = new LinkedList<>();

            List<String> headers = new ArrayList<>();
            Set<Map.Entry<Field, Method>> entrySet = writeFieldMethod.entrySet();
            for (Map.Entry<Field, Method> fieldMethodEntry : entrySet) {
                Field field = fieldMethodEntry.getKey();
                Excel excel = field.getAnnotation(Excel.class);
                headers.add(excel.name());
            }
            CSVParser csvParser = CSVParser.parse(inputStream, Charset.forName("GBK"), CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])));
            Iterator<CSVRecord> rows = csvParser.getRecords().iterator();
            //跳过表头
            rows.next();
            while (rows.hasNext()) {
                CSVRecord csvRecord = rows.next();
                T object = clazz.newInstance();
                int i = 0;
                for (Map.Entry<Field, Method> fieldMethodEntry : entrySet) {
                    Field field = fieldMethodEntry.getKey();
                    Excel excel = field.getAnnotation(Excel.class);
                    Method method = fieldMethodEntry.getValue();

                    String value = csvRecord.get(i);
                    if (field.getType() == String.class) {
                        method.invoke(object, getReplace(excel, value));
                    } else if (field.getType().getName().indexOf("java.lang.") == 0) {
                        // java.lang下面类型通用转换函数
                        String replace = getReplace(excel, value);
                        if (StringUtils.isNotBlank(replace) && StringUtils.isNumeric(replace)) {
                            Class<?> fieldClass = Class.forName(field.getType().getName());
                            Method parseMethod = fieldClass.getMethod("parse" + fixParse(field.getType().getSimpleName()), String.class);
                            if (method != null) {
                                Object ret = parseMethod.invoke(null, replace);
                                method.invoke(object, ret);
                            }
                        }
                    } else if (field.getType() == LocalDateTime.class) {
                        if (StringUtils.isNotBlank(value)) {
                            method.invoke(object, LocalDateTime.parse(value, DateTimeFormatter.ofPattern(excel.format())));
                        }
                    } else if (Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                        Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                        Class<?>[] actualTypeArguments = new Class<?>[types.length];
                        for (int j = 0; j < types.length; j++) {
                            actualTypeArguments[j] = (Class<?>) types[j];
                        }

                        method.invoke(object, JsonUtil.fromJson(value, field.getType(), actualTypeArguments));
                    } else {
                        method.invoke(object, JsonUtil.fromJson(value, field.getType()));
                    }
                    i++;
                }
                results.add(object);
            }
            return results;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("导入CSV失败");
        }
    }

    private LinkedHashMap<Field, Method> writeMethod(Class<?> clazz) {
        LinkedHashMap<Field, Method> writeFieldMethod = new LinkedHashMap<>();

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Excel excel = field.getAnnotation(Excel.class);

            if (excel != null) {
                PropertyDescriptor pd;
                try {
                    pd = new PropertyDescriptor(field.getName(), clazz);
                } catch (IntrospectionException e) {
                    log.error(e.getMessage(), e);
                    throw new RuntimeException("导出Excel异常", e);
                }
                writeFieldMethod.put(field, pd.getWriteMethod());
            }

        }

        return writeFieldMethod;
    }

    private String getReplace(Excel excel, String vaule) {
        String replace = excel.replace();
        if (StringUtils.isBlank(replace)) {
            return vaule;
        }
        String[] strings = replace.split(" ");
        Map<String, String> map = new HashMap<>();
        for (String str : strings) {
            String[] strings1 = str.split("-");
            map.put(strings1[1], strings1[0]);
        }
        return map.get(vaule);
    }

    private String fixParse(String type) {
        switch (type) {
            case "Integer":
                return "Int";
            default:
                return type;
        }
    }
}
