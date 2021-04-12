package br.gov.rs.defensoria.removal.core

import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.core.test.office.Offices
import br.gov.rs.defensoria.removal.core.test.office.Persons
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceTwoWinnersInThree {

	@Test
    void testGetNewAllocations() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(Persons.PEDRO, Offices.NOVO_HAMBURGO, [Offices.SAPIRANGA, Offices.SEBERI, Offices.POUSO_NOVO])
				.addCandidature(Persons.MARIA, Offices.SAPIRANGA, [Offices.ALVORADA, Offices.SAPIRANGA, Offices.GRAVATAI])
				.addCandidature(Persons.PAULA, Offices.ALVORADA, [Offices.SAPIRANGA, Offices.GRAVATAI, Offices.NOVO_HAMBURGO])
				.appendChoosables([Offices.CAMPO_BOM, Offices.CAXIAS_DO_SUL, Offices.PORTO_ALEGRE])
				.setNotChoosableOffices([Offices.SEBERI, Offices.POUSO_NOVO])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(Persons.PAULA, Offices.SAPIRANGA)
				.expectWin(Persons.MARIA, Offices.ALVORADA)
				.expectWinNothing(Persons.PEDRO)
				.expectOfficeNoWinner([Offices.CAMPO_BOM, Offices.CAXIAS_DO_SUL, Offices.NOVO_HAMBURGO, Offices.PORTO_ALEGRE, Offices.GRAVATAI])
	}
}
