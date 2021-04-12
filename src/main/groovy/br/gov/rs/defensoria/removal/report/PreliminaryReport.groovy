package br.gov.rs.defensoria.removal.report

import br.gov.rs.defensoria.removal.report.presentation.ResultTablePresenter
import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JRException
import net.sf.jasperreports.engine.JRExporterParameter
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.export.JRCsvExporter
import net.sf.jasperreports.engine.export.JRCsvExporterParameter
import net.sf.jasperreports.engine.export.JRRtfExporter
import net.sf.jasperreports.engine.export.JRXlsExporterParameter
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
import java.util.HashMap
import java.util.Map


class PreliminaryReport {

    String defaultFormats =  ['pdf', 'html', 'xls', 'csv', 'rtf']

    def generatePreliminaryReport(ResultTablePresenter result, String dateFechamento, String localPath, String fileName, String format) throws JRException, Exception {
        try{
            if(!defaultFormats.contains(format))
                throw new Exception("A aplicação suporta apenas os seguintes formatos: pdf, html, xls,csv e rtf")
            else
                try{
                    if(!(new File(localPath+fileName+"."+format).exists())){
                        this.generate(result,dateFechamento,localPath,fileName, format)
                    }
                    else{
                        throw new Exception("Arquivo "+format+" já existente no servidor")
                    }
                }
                catch(Exception e){
                    System.out.println(e.getMessage())
                }
        }
        catch(Exception e){
            System.out.println(e.getMessage())
        }
    }

    void generate(ResultTablePresenter result, String dateFechamento, String localPath, String fileName, String format){
        def template=getClass().getResourceAsStream('/jasperTemplates/preliminaryReport.jasper')
        result.rows.each {
            if(it.allocationName!=null){
                it.allocationName=new Utils().abreviarNome(it.allocationName)
            }else{
                it.allocationName="Sem Lotação"
            }
            if(it.pretensionName!=null){
                it.pretensionName=new Utils().abreviarNome(it.pretensionName)
            }else{
                it.pretensionName="Pretensão nula"
            }
        }
        JRDataSource datasource = new JRBeanCollectionDataSource(result.rows, true)
        Map<String, Object> parametros = new HashMap<String, Object>()
        parametros.put("_DATA_FECHAMENTO",dateFechamento)
        JasperPrint print = JasperFillManager.fillReport(template, parametros, datasource)
        if(format=="pdf")
            try {
                JasperExportManager.exportReportToPdfFile(print, localPath+fileName+".pdf")
            }
            catch (JRException e1) {
                e1.printStackTrace()
                return
            }
        else if(format=="html")
            try {
                JasperExportManager.exportReportToHtmlFile(print,localPath+fileName+".html")
            }
            catch (JRException e1) {
                e1.printStackTrace()
                return
            }

        else if(format=="csv")
            try {
                JRCsvExporter exporterCSV = new JRCsvExporter()
                exporterCSV.setParameter(JRCsvExporterParameter.JASPER_PRINT, print)
                exporterCSV.setParameter(JRCsvExporterParameter.OUTPUT_FILE_NAME, localPath+fileName+".csv")
                exporterCSV.exportReport()
            }
            catch (JRException e1) {
                e1.printStackTrace()
                return
            }
        else if(format=="xls")
            try {
                JRXlsxExporter exporterXLSXReporter = new JRXlsxExporter()
                exporterXLSXReporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,print)
                exporterXLSXReporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, localPath+fileName+".xls")
                exporterXLSXReporter.exportReport()
            }
            catch (JRException e1) {
                e1.printStackTrace()
                return
            }
    }
}