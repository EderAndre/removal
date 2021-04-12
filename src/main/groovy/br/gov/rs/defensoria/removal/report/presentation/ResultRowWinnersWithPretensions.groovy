package br.gov.rs.defensoria.removal.report.presentation

import br.gov.rs.defensoria.removal.domain.Office

class ResultRowWinnersWithPretensions {
	int antiquity
	String name
	String originOffice
	String wonOffice
	Integer wonOfficeId
	Integer wonOrder
	List<Pretension> pretensions
}

class Pretension {
	boolean wanted
	int order
	int officeId
	boolean hasOwner = false
}