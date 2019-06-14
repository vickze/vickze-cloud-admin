package io.vickze.common.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class ImportCsv {


    public List<CSVRecord> importCsv(InputStream inputStream) {
        try {
            CSVParser csvParser = CSVParser.parse(inputStream, Charset.forName("GBK"), CSVFormat.DEFAULT.withHeader("clientName", "phone"));
            return csvParser.getRecords();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("导入CSV失败");
        }
    }
}
