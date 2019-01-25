package service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;

public final class test {
    public static void testPdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletOutputStream servletOutputStream = response.getOutputStream();
        File reportFile = new File("/home/hearen/git/personal/AboutJava/demos/JasperServlet/src/main/resources/jasperreports_demo.jasper");
        byte[] bytes = null;

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.jrxml");
            bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
                    new HashMap(), new JREmptyDataSource());

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
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, new HashMap(), new JREmptyDataSource());
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
