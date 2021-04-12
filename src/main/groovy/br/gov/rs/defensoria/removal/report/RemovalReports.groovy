package br.gov.rs.defensoria.removal.report

import net.sf.jasperreports.engine.JRException
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRCsvDataSource
import net.sf.jasperreports.engine.export.JRCsvExporter
import net.sf.jasperreports.engine.export.JRCsvExporterParameter
import net.sf.jasperreports.engine.export.JRXlsExporterParameter
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
import net.sf.jasperreports.engine.util.JRLoader
import br.gov.rs.defensoria.removal.report.presentation.ResultTableWinnersWithPretensions

class RemovalReports {
    private String defaultFormats =  ['pdf', 'html', 'xls', 'csv']

    void generate(ResultTableWinnersWithPretensions result, String closedDate, String reportPath, String fileName, String format) throws JRException, Exception {
        try{
            if(!defaultFormats.contains(format))
                throw new Exception("A aplicação suporta apenas os seguintes formatos: pdf, html, xls e csv")
            else
                try{
                    if(!(new File("${reportPath}${fileName}.${format}").exists())){
                        this.generateXML(result,closedDate,reportPath,fileName)
                        this.generateArray(result,reportPath,fileName)
                        this.exportRemovalReport(reportPath,fileName, format)
                    }
                    else{
                        throw new Exception("Arquivo já existente no servidor")
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage())
                }
        }catch(Exception e){
            System.out.println(e.getMessage())
        }
    }
    private String generateArray(ResultTableWinnersWithPretensions resultTable, String localPath, String fileName) throws JRException, Exception {
        List<String> fields=['antiquity', 'name', 'originOffice', 'wonOffice', 'wonOrder']
        resultTable.officesList.each { office -> fields.add(office.name) }
        List correctFields=[]
        fields.each{
            String correction="${it}".replace( 'º', 'o').replace( 'ª', '').trim()
            correctFields.add(correction)
        }
        List<String> total=[]
        total.add(correctFields.join("|"))
        resultTable.rows.each {
            List<String>row=[]
            def giveUp=true
            it.pretensions.each {  
                        t -> if(t.order!=0){ 
                                        giveUp=false
                                        return
                        }
                     }
            if(!giveUp){
            row.addAll([it.antiquity, it.name, it.originOffice? new Utils().abreviarNome(it.originOffice):"Sem Lotação", it.wonOffice, it.wonOrder])
            it.pretensions.each {  p -> row.add(p.order==0 ? " " : p.order)}
            total.add(row.join("|"))
            }

        }
        String array=total.join("\n")
        return this.generateFileCSV(localPath,fileName,array)
    }
    private void generateFileCSV(String localPath, String fileName, String data){
        new File(localPath+'\$'+fileName+'.jasper').withWriter{	it.println data}
    }
    private String jasperObjectProvider(JasperPrint printObj, String fileName) throws JRException, Exception {
        def format=fileName.substring(fileName.length()-3)
        try{
            if(format=="pdf"){
                JasperExportManager.exportReportToPdfFile(printObj,fileName)
            }
            else if(format=="html"){
                JasperExportManager.exportReportToHtmlFile(printObj, fileName)
            }
            else if(format=="csv"){
                JRCsvExporter exporterCSV = new JRCsvExporter()
                exporterCSV.setParameter(JRCsvExporterParameter.JASPER_PRINT, printObj)
                exporterCSV.setParameter(JRCsvExporterParameter.OUTPUT_FILE_NAME, fileName)
                exporterCSV.exportReport()
            }
            else if(format=="xls") {
                JRXlsxExporter exporterXLSXReporter = new JRXlsxExporter()
                exporterXLSXReporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,printObj)
                exporterXLSXReporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, fileName)
                exporterXLSXReporter.exportReport()
            }
        }catch (JRException e1) {
            e1.printStackTrace()
            return
        }
    }

    void exportRemovalReport(String localPath, String fileName, String format){
        def completFileName= localPath+fileName+"."+format
        JRCsvDataSource ds = new JRCsvDataSource(JRLoader.getLocationInputStream(localPath+'\$'+fileName+".jasper"))
        ds.setUseFirstRowAsHeader(true)
        ds.setFieldDelimiter("|" as char)
        JasperReport report = JasperCompileManager.compileReport(localPath+fileName+".jrxml")
        JasperPrint print = JasperFillManager.fillReport(report, null, ds)
        this.jasperObjectProvider(print, completFileName)
    }
    private void generateXML(ResultTableWinnersWithPretensions resultTable,String closedDate,String localPath, String fileName){
        new ExportPretensionsToXML().export(resultTable,closedDate, localPath,fileName)
    }
}
