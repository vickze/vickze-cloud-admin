package io.vickze.common.excel;

import io.vickze.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class ImportExcel {

    /**
     * 导入Excel
     */
    public <T> List<T> importExcel(InputStream inputStream, Class<T> clazz) {
        LinkedHashMap<Field, Method> writeFieldMethod = writeMethod(clazz);

        List<T> results = new LinkedList<>();
        try {
            Workbook book = WorkbookFactory.create(inputStream);
            for (int sheetIndex = 0, sheetSize = book.getNumberOfSheets(); sheetIndex < sheetSize; sheetIndex++) {
                Sheet sheet = book.getSheetAt(sheetIndex);
                Iterator<Row> rows = sheet.rowIterator();
                //跳过表头
                rows.next();
                //内容
                while (rows.hasNext()) {
                    Row row = rows.next();
                    T object = clazz.newInstance();
                    int i = 0;
                    Set<Map.Entry<Field, Method>> entrySet = writeFieldMethod.entrySet();
                    for (Map.Entry<Field, Method> fieldMethodEntry : entrySet) {
                        Field field = fieldMethodEntry.getKey();
                        Method method = fieldMethodEntry.getValue();
                        Excel excel = field.getAnnotation(Excel.class);
                        Cell rowCell = row.getCell(i);
                        if (excel.importIgnore() || rowCell == null) {
                            i++;
                            continue;
                        }
                        if (StringUtils.isBlank(rowCell.toString())) {
                            if (!excel.importNullable()) {
                                throw new RuntimeException(excel.name() + "不能为空");
                            }
                        }
                        if (field.getType() == String.class) {
                            method.invoke(object, getReplace(excel, rowCell));
                        } else if (field.getType().getName().indexOf("java.lang.") == 0) {
                            // java.lang下面类型通用转换函数
                            String replace = getReplace(excel, rowCell);
                            if (StringUtils.isNotBlank(replace) && StringUtils.isNumeric(replace)) {
                                Class<?> fieldClass = Class.forName(field.getType().getName());
                                Method parseMethod = fieldClass.getMethod("parse" + fixParse(field.getType().getSimpleName()), String.class);
                                if (method != null) {
                                    Object ret = parseMethod.invoke(null, replace);
                                    method.invoke(object, ret);
                                }
                            }
                        } else if (field.getType() == LocalDateTime.class) {
                            if (StringUtils.isNotBlank(rowCell.getStringCellValue())) {
                                method.invoke(object, LocalDateTime.parse(rowCell.getStringCellValue(), DateTimeFormatter.ofPattern(excel.format())));
                            }
                        } else if (Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                            Type[] types = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                            Class<?>[] actualTypeArguments = new Class<?>[types.length];
                            for (int j = 0; j < types.length; j++) {
                                actualTypeArguments[j] = (Class<?>) types[j];
                            }

                            method.invoke(object, JsonUtil.fromJson(rowCell.getStringCellValue(), field.getType(), actualTypeArguments));
                        } else {
                            method.invoke(object, JsonUtil.fromJson(rowCell.toString(), field.getType()));
                        }

                        i++;
                    }

                    results.add(object);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("导入Excel失败");
        }

        return results;
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
                    e.printStackTrace();
                    throw new RuntimeException("导出Excel异常", e);
                }
                writeFieldMethod.put(field, pd.getWriteMethod());
            }

        }

        return writeFieldMethod;
    }

    private String getReplace(Excel excel, Cell cell) {
        String replace = excel.replace();
        if (StringUtils.isBlank(replace)) {
            return cell.toString();
        }
        String[] strings = replace.split(" ");
        Map<String, String> map = new HashMap<>();
        for (String str : strings) {
            String[] strings1 = str.split("-");
            map.put(strings1[1], strings1[0]);
        }
        return map.get(cell.getStringCellValue());
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
