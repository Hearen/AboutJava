import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class JasperReportsIntro {
    public static void main(String[] args)
    {
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try
        {
            jasperReport = JasperCompileManager.compileReport(
                    "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.jrxml");
            JasperCompileManager.compileReportToFile(
                    "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.jrxml", // the path to the jrxml file to compile
                    "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.jasper");
            jasperPrint = JasperFillManager.fillReport(
                    jasperReport, new HashMap(), new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(
                    jasperPrint, "/home/hearen/git/personal/AboutJava//demos/JasperServlet/src/main/resources/jasperreports_demo.pdf");
        }
        catch (JRException e)
        {
            e.printStackTrace();
        }
    }
}
