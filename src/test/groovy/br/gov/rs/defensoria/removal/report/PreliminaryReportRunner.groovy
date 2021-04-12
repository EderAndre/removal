package br.gov.rs.defensoria.removal.report

import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.report.presentation.ResultRowPresenter
import br.gov.rs.defensoria.removal.report.presentation.ResultTablePresenter

class PreliminaryReportRunner {

	
	static void main(String[] args) {
		
		
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
		
		def report = new PreliminaryReport()
		table.rows.each{
		println it.pretensionName
		}
		println table
		report.generatePreliminaryReport(table,'25/05/2015 08:00:00', "build/reports/","preliminay1","pdf")
	
		
		
	}
}
