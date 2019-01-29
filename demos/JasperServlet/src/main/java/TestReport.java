package service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;

public class TestReport {

    public TestReport() {}
    public static void testPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        File reportFile = new File("/home/hearen/git/personal/AboutJava/demos/JasperServlet/src/main/resources/jasperreports_demo.jasper");
        byte[] bytes = null;

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.jrxml");
//            bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
//                    new HashMap(), new JREmptyDataSource());
            ReportService reportService = new ReportService();
            bytes = reportService.getReportPdfBytes();

            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);

            servletOutputStream.write(bytes, 0, bytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (JRException e) {
            handleException(response, e);
        }
    }


    private static  void handleException(HttpServletResponse response, Exception e) throws IOException {
        // display stack trace in the browser
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        response.setContentType("text/plain");
        response.getOutputStream().print(stringWriter.toString());
    }

    public static void testHtml(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            ServletOutputStream servletOutputStream = response.getOutputStream();
//            JasperReport jasperReport = JasperCompileManager.compileReport(
//                    "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.jrxml");
//            JasperPrint jasperPrint = JasperFillManager.fillReport(
//                    jasperReport, new HashMap(), new JREmptyDataSource());
            ReportService reportService = new ReportService();
            JasperPrint jasperPrint = reportService.getReportPrint();
            HtmlExporter exporter = new HtmlExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getOutputStream()));
            exporter.exportReport();
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (JRException e) {
            handleException(response, e);
        }
    }

}
