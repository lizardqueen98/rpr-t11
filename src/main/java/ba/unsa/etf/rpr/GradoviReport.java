package ba.unsa.etf.rpr;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class GradoviReport extends JFrame {
    public void showReport(Connection conn, Drzava drzava) throws JRException {
        try {

            InputStream reportSrcFile;
        if(drzava==null){
                reportSrcFile = getClass().getResource("/reports/gradovi.jrxml").openStream();
        }
        else reportSrcFile = getClass().getResource("/reports/gradov2i.jrxml").openStream();
        InputStream reportsDir = getClass().getResource("/reports/").openStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
        // Fields for resources path
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("reportsDirPath", reportsDir);
        if(drzava!=null) parameters.put("drzava", drzava.getId());
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        list.add(parameters);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void saveAs(String format, Connection conn){
        String reportSrcFile = getClass().getResource("/reports/gradovi.jrxml").getFile();
        String reportsDir = getClass().getResource("/reports/").getFile();

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("reportsDirPath", reportsDir);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list.add(parameters);
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);
            File file = new File(format);
            OutputStream output = new FileOutputStream(file);
            if (format.contains(".pdf"))
                JasperExportManager.exportReportToPdfStream(print, output);
            else if (format.contains(".docx")) {
                JRDocxExporter exporter = new JRDocxExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                exporter.exportReport();
            } else if (format.contains(".xslx")) {
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(print));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
                SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
                configuration.setOnePagePerSheet(true);
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            }
            output.close();
        }catch(Exception e){

        }
    }
}
