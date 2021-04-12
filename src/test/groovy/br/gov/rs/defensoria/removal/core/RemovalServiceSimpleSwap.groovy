package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.core.test.office.Offices
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceSimpleSwap {

	@Test
    void testGetNewAllocations() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, ALVORADA, [SAPIRANGA, PORTO_ALEGRE, POUSO_NOVO])
				.addCandidature(MARIA, SAPIRANGA, [ALVORADA, SAPIRANGA, GRAVATAI])
				.appendChoosables([CAMPO_BOM, CAXIAS_DO_SUL, NOVO_HAMBURGO])
				.setNotChoosableOffices([POUSO_NOVO])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(PEDRO, SAPIRANGA)
				.expectWin(MARIA, ALVORADA)
				.expectOfficeNoWinner([CAMPO_BOM, CAXIAS_DO_SUL, NOVO_HAMBURGO, PORTO_ALEGRE, GRAVATAI])
	}

}
