package io.vickze.excel;

import io.vickze.common.excel.ExportCsv;
import io.vickze.common.excel.ExportExcel;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ExportTest {

    @Test
    public void bigDataExport() throws Exception {

        List<MsgClient> list = new LinkedList<>();
        Date start = new Date();

        ExportExcel exportExcel = new ExportExcel();
        for (int i = 0; i < 1000000; i++) {  //一百万数据量
            MsgClient client = new MsgClient();
            client.setBirthday(new Date());
            client.setClientName("小明" + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("JueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            list.add(client);
            if (list.size() == 100000) {
                exportExcel = exportExcel.append(MsgClient.class, list, "大数据导出", false);
                list.clear();
            }
        }

        File savefile = new File("C:\\Users\\25125\\Java\\zookeeper-3.4.11\\bin");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("C:\\Users\\25125\\Java\\zookeeper-3.4.11\\bin\\ExcelExportBiga.bigDataExport.xlsx");
        exportExcel.write(fos);
        fos.close();
        System.out.println(new Date().getTime() - start.getTime());
    }

    @Test
    public void bigCsvExport() throws Exception {
        List<MsgClient> list = new LinkedList<>();
        Date start = new Date();

        ExportCsv exportExcel = new ExportCsv(true);
        for (int i = 0; i < 1000000; i++) {  //一百万数据量
            MsgClient client = new MsgClient();
            client.setBirthday(new Date());
            client.setClientName("小明" + i);
            client.setClientPhone("18797" + i);
            client.setCreateBy("JueYue");
            client.setId("1" + i);
            client.setRemark("测试" + i);
            list.add(client);
            if (list.size() == 100000) {
                exportExcel = exportExcel.append(MsgClient.class, list, "大数据", true);
                list.clear();
            }
        }

        File savefile = new File("C:\\Users\\25125\\Java\\zookeeper-3.4.11\\bin");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("C:\\Users\\25125\\Java\\zookeeper-3.4.11\\bin\\ExcelExportBiga.bigDataExport" + exportExcel.getExportSuffix());
        exportExcel.write(fos);
        fos.close();
        System.out.println(new Date().getTime() - start.getTime());
    }


}
