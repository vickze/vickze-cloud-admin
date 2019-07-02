package io.vickze.common.excel;


import io.vickze.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
public class ExportExcel {

    private static final int MAX_SHEET_EXPORT = 1048576;

    private Workbook workBook;

    private Sheet sheet;

    private int rowIndex;


    public ExportExcel() {
        workBook = new SXSSFWorkbook();
    }

    public ExportExcel append(Class<?> clazz, Collection<?> dataCollection) {
        return append(clazz, dataCollection, null);
    }

    public ExportExcel append(Class<?> clazz, Collection<?> dataCollection, String sheetName) {
        return append(clazz, dataCollection, sheetName, false);
    }


    public ExportExcel append(Class<?> clazz, Collection<?> dataCollection, String sheetName, boolean newSheet) {
        LinkedHashMap<Field, Method> readFieldMethod = readMethod(clazz);
        Set<Map.Entry<Field, Method>> entrySet = readFieldMethod.entrySet();
        if (sheet == null || newSheet) {
            sheet = StringUtils.isNotBlank(sheetName) ? workBook.createSheet(getSheetName(sheetName)) : workBook.createSheet();
            rowIndex = 0;
            addHeaderRow(entrySet);
        }

        for (Object data : dataCollection) {
            if (rowIndex == MAX_SHEET_EXPORT) {
                sheet = StringUtils.isNotBlank(sheetName) ?
                        workBook.createSheet(getSheetName(sheetName)) : workBook.createSheet();
                rowIndex = 0;
                addHeaderRow(entrySet);
            }
            Row dataRow = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (Map.Entry<Field, Method> fieldMethodEntry : entrySet) {
                Field field = fieldMethodEntry.getKey();
                Method method = fieldMethodEntry.getValue();
                try {
                    Excel excel = field.getAnnotation(Excel.class);
                    Object o = method.invoke(data);
                    Cell cell = dataRow.createCell(cellIndex);
                    if (o == null) {
                        cell.setCellValue(StringUtils.EMPTY);
                    } else if (o.getClass().getName().indexOf("java.lang.") == 0) {
                        cell.setCellValue(getReplace(excel, o));
                    } else if (o instanceof LocalDateTime) {
                        cell.setCellValue(((LocalDateTime) o).format(DateTimeFormatter.ofPattern(excel.format())));
                    } else {
                        cell.setCellValue(JsonUtil.toJson(o));
                    }
                    cellIndex++;
                } catch (Exception e) {
                    throw new RuntimeException("导出Excel失败", e);
                }
            }
        }

        return this;
    }

    private void addHeaderRow(Set<Map.Entry<Field, Method>> entrySet) {
        //表头
        Row headerRow = sheet.createRow(rowIndex++);
        int cellIndex = 0;
        for (Map.Entry<Field, Method> fieldMethodEntry : entrySet) {
            Field field = fieldMethodEntry.getKey();
            Excel excel = field.getAnnotation(Excel.class);
            Cell cell = headerRow.createCell(cellIndex);
            cell.setCellValue(excel.name());
            cellIndex++;
        }
    }

    private String getSheetName(String sheetName) {
        return getSheetName(sheetName, null);
    }

    private String getSheetName(String sheetName, Integer i) {
        String sheetNameTemp = i == null ? sheetName : sheetName + "_" + i;
        if (workBook.getSheet(sheetNameTemp) != null) {
            return getSheetName(sheetName, i == null ? 1 : i + 1);
        }
        return sheetNameTemp;
    }



    private LinkedHashMap<Field, Method> readMethod(Class<?> clazz) {
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
                    throw new RuntimeException("导出Excel异常", e);
                }
                writeFieldMethod.put(field, pd.getReadMethod());
            }

        }

        return writeFieldMethod;
    }

    private static String getReplace(Excel excel, Object o) {
        String replace = excel.replace();
        if (StringUtils.isBlank(replace)) {
            return o.toString();
        }

        String[] strings = replace.split(" ");
        Map<String, String> map = new HashMap<>();
        for (String str : strings) {
            String[] strings1 = str.split("-");
            map.put(strings1[0], strings1[1]);
        }

        return map.get(String.valueOf(o));
    }

    public void write(OutputStream outputStream) throws IOException {
        workBook.write(outputStream);
    }
}
