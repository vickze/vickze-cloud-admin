package io.vickze.excel;


import io.vickze.common.csv.ImportCsv;
import io.vickze.common.excel.ImportExcel;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ImportTest {

    @Test
    public void excelImportTest() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\25125\\Java\\zookeeper-3.4.11\\bin\\ExcelExportBiga.bigDataExport.xlsx");
        List<MsgClient> list = new ImportExcel().importExcel(fileInputStream, MsgClient.class);
        System.out.println(list.size());
    }

    @Test
    public void csvImportTest() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\25125\\Java\\zookeeper-3.4.11\\bin\\ExcelExportBiga.bigDataExport.csv");
        List<CSVRecord> list = new ImportCsv().importCsv(fileInputStream);
        System.out.println(list.size());
    }
}
