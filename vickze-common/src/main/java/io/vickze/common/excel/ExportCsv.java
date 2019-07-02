package io.vickze.common.excel;

import io.vickze.common.util.JsonUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExportCsv {

    private static final int MAX_SHEET_EXPORT = 1048576;

    private Map<String, StringWriter> csvMap;

    private StringWriter stringWriter;

    private CSVPrinter csvPrinter;

    /**
     * 多个csv时默认文件名称
     */
    private String defaultFileName;

    private int rowIndex;

    private boolean forExcel;

    public ExportCsv() {
        this.csvMap = new LinkedHashMap<>();
        this.defaultFileName = "Export";
    }

    public ExportCsv(String defaultFileName) {
        this.csvMap = new LinkedHashMap<>();
        this.defaultFileName = defaultFileName;
    }

    public ExportCsv(boolean forExcel) {
        this.csvMap = new LinkedHashMap<>();
        this.defaultFileName = "Export";
        this.forExcel = forExcel;
    }

    public ExportCsv(String defaultFileName, boolean forExcel) {
        this.csvMap = new LinkedHashMap<>();
        this.defaultFileName = defaultFileName;
        this.forExcel = forExcel;
    }


    public ExportCsv append(Class<?> clazz,  Collection<?> dataCollection) throws IOException {
        return append(clazz, dataCollection, null);
    }

    public ExportCsv append(Class<?> clazz,  Collection<?> dataCollection, String fileName) throws IOException {
        return append(clazz, dataCollection, fileName, false);
    }

    public ExportCsv append(Class<?> clazz,  Collection<?> dataCollection, String fileName, boolean newFile) throws IOException {
        LinkedHashMap<Field, Method> readFieldMethod = readMethod(clazz);
        Set<Map.Entry<Field, Method>> entrySet = readFieldMethod.entrySet();
        List<String> headers =new ArrayList<>();
        for (Map.Entry<Field, Method> fieldMethodEntry : entrySet) {
            Field field = fieldMethodEntry.getKey();
            Excel excel = field.getAnnotation(Excel.class);
            headers.add(excel.name());
        }

        if (stringWriter == null || newFile) {
            stringWriter = new StringWriter();
            csvMap.put(getFileName(StringUtils.isNotBlank(fileName) ? fileName : defaultFileName), stringWriter);
            //创建CSVPrinter对象
            csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])));
            rowIndex = 1;
        }


        for (Object data : dataCollection) {
            if (forExcel && rowIndex == MAX_SHEET_EXPORT) {
                stringWriter = new StringWriter();
                csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])));
                rowIndex = 1;
                csvMap.put(getFileName(StringUtils.isNotBlank(fileName) ? fileName : defaultFileName), stringWriter);
            }

            List<String> dataRow = new ArrayList<>();
            for (Map.Entry<Field, Method> fieldMethodEntry : entrySet) {
                Field field = fieldMethodEntry.getKey();
                Method method = fieldMethodEntry.getValue();
                try {
                    Excel excel = field.getAnnotation(Excel.class);
                    Object o = method.invoke(data);
                    if (o == null) {
                        dataRow.add(StringUtils.EMPTY);
                    } else if (o.getClass().getName().indexOf("java.lang.") == 0) {
                        dataRow.add(getReplace(excel, o));
                    } else if (o instanceof LocalDateTime) {
                        dataRow.add(((LocalDateTime) o).format(DateTimeFormatter.ofPattern(excel.format())));
                    } else {
                        dataRow.add(JsonUtil.toJson(o));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("导出Excel失败", e);
                }
            }
            csvPrinter.printRecord(dataRow.toArray(new String[0]));
            rowIndex++;
        }

        return this;
    }

    public void write(OutputStream outputStream) throws IOException {
        if (csvMap.size() > 1) {
            //导出为zip
            ZipOutputStream zip = new ZipOutputStream(outputStream);

            for (Map.Entry<String, StringWriter> csv : csvMap.entrySet()) {

                // 添加到zip
                zip.putNextEntry(new ZipEntry(csv.getKey() + ".csv"));
                IOUtils.write(csv.getValue().toString(), zip, "GBK");
                IOUtils.closeQuietly(csv.getValue());
                zip.closeEntry();
            }

            IOUtils.closeQuietly(zip);
        } else {
            //导出为csv
            IOUtils.write(csvMap.entrySet().iterator().next().getValue().toString(), outputStream, "GBK");
        }

    }

    /**
     * 导出后缀
     */
    public String getExportSuffix() {
        return csvMap.size() > 1 ? ".zip" : ".csv";
    }

    private String getFileName(String fileName) {
        return getFileName(fileName, null);
    }

    private String getFileName(String fileName, Integer i) {
        String fileNameTemp = i == null ? fileName : fileName + "_" + i;
        if (csvMap.get(fileNameTemp) != null) {
            return getFileName(fileName, i == null ? 1 : i + 1);
        }
        return fileNameTemp;
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
}
