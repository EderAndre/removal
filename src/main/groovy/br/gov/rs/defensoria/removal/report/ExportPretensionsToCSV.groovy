package br.gov.rs.defensoria.removal.report

import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.domain.Person
import br.gov.rs.defensoria.removal.report.presentation.ResultRowWinnersWithPretensions
import br.gov.rs.defensoria.removal.report.presentation.ResultTableWinnersWithPretensions

class ExportPretensionsToCSV {
	List<String> defaultColumns = ['Antiguidade', 'Pessoa', 'Origem', 'Destino']
	
	List<String> generateHeader(ResultTableWinnersWithPretensions resultTable) {
		List<String> headerColumns = defaultColumns.collect()
		resultTable.officesList.each { office -> headerColumns.add(office.name) }
		return headerColumns
	}
	
	List<String> generateRow(ResultRowWinnersWithPretensions rowResult) {
		List<String> row = []
		row.add(rowResult.antiquity)
		row.add(rowResult.name)
		row.add(rowResult.originOffice)
		row.add(rowResult.wonOffice)
        rowResult.pretensions.each { p -> row.add(p.order) }
		return row
	}
	
	String convertListToCSV(List<String> list) {
		String row = ''
		list.each {	row += "${it}," }	
		return row
	}
	
	def export(ResultTableWinnersWithPretensions resultTable) {
		StringBuilder result = new StringBuilder()

		String cabecalho = convertListToCSV(generateHeader(resultTable))
		result.append(cabecalho + System.lineSeparator)

		String row = ''
		resultTable.rows.each {
			row = convertListToCSV(generateRow(it))
			result.append(row + System.lineSeparator)
		}
		
		return result
	}
}
