package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceThreeWinners {

	@Test
    void testGetNewAllocations() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, NOVO_HAMBURGO, [SAPIRANGA, SEBERI, GRAVATAI])
				.addCandidature(MARIA, SAPIRANGA, [ALVORADA, SAPIRANGA, GRAVATAI])
				.addCandidature(PAULA, ALVORADA, [SAPIRANGA, GRAVATAI, NOVO_HAMBURGO])
				.appendChoosables([CAMPO_BOM, CAXIAS_DO_SUL, PORTO_ALEGRE])
				.setNotChoosableOffices([SEBERI])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(PAULA, SAPIRANGA)
				.expectWin(MARIA, ALVORADA)
				.expectWin(PEDRO, GRAVATAI)
				.expectOfficeNoWinner([CAMPO_BOM, CAXIAS_DO_SUL, NOVO_HAMBURGO, PORTO_ALEGRE])
	}

}
