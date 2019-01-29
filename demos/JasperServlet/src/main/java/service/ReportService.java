package service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportService {
    public byte[] getReportPdfBytes() {
        try {
            return JasperExportManager.exportReportToPdf(getReportPrint());
        } catch (JRException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    public JasperPrint getReportPrint() {
        try {
            String pathToJrxmlTemplate = "/home/hearen/git/personal/AboutJava/demos/JasperServlet/src/main/resources/report-test.jrxml";
            JasperDesign jasperDesign = JRXmlLoader.load(pathToJrxmlTemplate);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            return JasperFillManager.fillReport(jasperReport, new HashMap<>(), new JRMapCollectionDataSource(Collections.<Map<String, ?>>singletonList(getMockContent())));
        } catch (JRException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    private Map<String, Object> getMockContent() {
        Map<String, Object> mainDataSource = new HashMap<>();
        mainDataSource.put("subject", getRandomSubject());
        mainDataSource.put("detail", "");
        mainDataSource.put("price", String.valueOf(1000));
        Long total = getRandomTotal();
        mainDataSource.put("tax", String.valueOf(getTax(total)));
        mainDataSource.put("total", String.valueOf(total));
        mainDataSource.put("baseDate", getValidDate());
        mainDataSource.put("applicationNo", "");
        String applicationId = "69812820-2387-11e9-806a-02427cc9618e";
        Long applicationVersion = 0L;
        mainDataSource.put("applicationVersion", String.valueOf(applicationVersion));
        return mainDataSource;
    }

    private int getRandomNonNegative(int len) {
        return new Random().nextInt(len);
    }

    private String getRandomSubject() {
        String[] subjects = {"drinks", "papers", "tissues", "nanny", "air conditioner"};
        return subjects[getRandomNonNegative(subjects.length)];
    }

    private String getValidDate() {
        return "2019-01-29T14:39:41.279+09:00[Asia/Tokyo]";
    }

    private Long getTax(Long total) {
        if (total > 1000) {
            return total / 20;
        }
        return total / 30;
    }

    private Long getRandomTotal() {
        return getRandomNonNegative(1000) + 500L;
    }

}
