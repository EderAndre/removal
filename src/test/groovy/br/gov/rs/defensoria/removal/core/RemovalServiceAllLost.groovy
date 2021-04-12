package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.core.test.office.Offices
import br.gov.rs.defensoria.removal.core.test.office.Persons
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceAllLost {

	@Test
    void testGetNewAllocations() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, NOVO_HAMBURGO, [SAPIRANGA, PORTO_ALEGRE, POUSO_NOVO])
				.addCandidature(MARIA, SAPIRANGA, [ALVORADA, SAPIRANGA, GRAVATAI])
				.addCandidature(PAULA, ALVORADA, [GRAVATAI, SAPIRANGA, NOVO_HAMBURGO])
				.addCandidature(JOSE, GRAVATAI, [PORTO_ALEGRE, NOVO_HAMBURGO, ALVORADA])
				.addCandidature(CARLOS, PORTO_ALEGRE, [GRAVATAI, ALVORADA, SAPIRANGA])
				.addCandidature(JERICO, MORRO_REUTER, [GRAVATAI, DOIS_IRMAOS, SAPIRANGA])
				.appendChoosables([CAMPO_BOM, CAXIAS_DO_SUL])
				.setNotChoosableOffices([POUSO_NOVO, DOIS_IRMAOS])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWinNothing(PEDRO)
				.expectWinNothing(MARIA)
				.expectWinNothing(PAULA)
				.expectWinNothing(JOSE)
				.expectWinNothing(CARLOS)
				.expectWinNothing(JERICO)
				.expectOfficeNoWinner([CAMPO_BOM, SAPIRANGA, CAXIAS_DO_SUL, NOVO_HAMBURGO, PORTO_ALEGRE, ALVORADA, GRAVATAI, MORRO_REUTER,])
	}
}
