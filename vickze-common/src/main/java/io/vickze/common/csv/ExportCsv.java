package io.vickze.common.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
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


    public ExportCsv append(String[] headers, Collection<String[]> dataCollection) throws IOException {
        return append(headers, dataCollection, null);
    }

    public ExportCsv append(String[] headers, Collection<String[]> dataCollection, String fileName) throws IOException {
        return append(headers, dataCollection, fileName, false);
    }

    public ExportCsv append(String[] headers, Collection<String[]> dataCollection, String fileName, boolean newFile) throws IOException {
        if (stringWriter == null || newFile) {
            stringWriter = new StringWriter();
            csvMap.put(getFileName(StringUtils.isNotBlank(fileName) ? fileName : defaultFileName), stringWriter);
            //创建CSVPrinter对象
            csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT.withHeader(headers));
            rowIndex = 1;
        }


        for (String[] data : dataCollection) {
            if (forExcel && rowIndex == MAX_SHEET_EXPORT) {
                stringWriter = new StringWriter();
                csvPrinter = new CSVPrinter(stringWriter, CSVFormat.DEFAULT.withHeader(headers));
                rowIndex = 1;
                csvMap.put(getFileName(StringUtils.isNotBlank(fileName) ? fileName : defaultFileName), stringWriter);
            }
            csvPrinter.printRecord(data);
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


}
