package br.gov.rs.defensoria.removal.report

import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.report.presentation.ResultRowPresenter
import br.gov.rs.defensoria.removal.report.presentation.ResultTablePresenter

import br.gov.rs.defensoria.removal.report.presentation.ResultTablePresenter
import net.sf.jasperreports.engine.JRDataSource
import net.sf.jasperreports.engine.JREmptyDataSource
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
import net.sf.jasperreports.engine.JasperCompileManager

class PreliminaryReportTest {

	@Test
    void testGeneratePdf() {
		
		//def report = new PreliminaryReport()
		ResultTablePresenter table = new ResultTablePresenter()	
		
		def rows = [
			[19, 'Brenda', 'Campina das Miss�es', 'Porto Alegre - UCAA'],
			[19, 'Brenda da Silveira Gol�aves Druta Catal�o', 'Campina das Miss�es', 'Porto Alegre - UCAA'],
			[19, 'Brenda', 'Campina das Miss�es', 'Porto Alegre - UCAA'],
		]
		
		rows.each { 
			table.rows.add( new ResultRowPresenter(
				antiquity: it[0], name: it[1], allocationName: it[2], pretensionName: it[3]
			))
		}
		
		
		
	}
}
