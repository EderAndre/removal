package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceSwapSeconds {

	@Test
    void testGetNewAllocations() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, CAXIAS_DO_SUL, [PORTO_ALEGRE, SAPIRANGA, CAXIAS_DO_SUL])
				.addCandidature(MARIA, GRAVATAI, [PORTO_ALEGRE, SAPIRANGA, GRAVATAI])
				.addCandidature(PAULA, ALVORADA, [GRAVATAI, SAPIRANGA, NOVO_HAMBURGO])
				.addCandidature(JOSE, SAPIRANGA, [CAXIAS_DO_SUL, MORRO_REUTER, ALVORADA])
				.addCandidature(CARLOS, PORTO_ALEGRE, [SAPIRANGA, MORRO_REUTER, CAXIAS_DO_SUL])
				.addCandidature(JERICO, MORRO_REUTER, [POUSO_NOVO, DOIS_IRMAOS, SAPIRANGA])
				.appendChoosables([CAMPO_BOM])
				.setNotChoosableOffices([POUSO_NOVO, DOIS_IRMAOS])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(JERICO, SAPIRANGA)
				.expectWin(MARIA, PORTO_ALEGRE)
				.expectWin(JOSE, ALVORADA)
				.expectWin(PAULA, GRAVATAI)
				.expectWin(CARLOS, MORRO_REUTER)
				.expectOfficeNoWinner([CAMPO_BOM, CAXIAS_DO_SUL, NOVO_HAMBURGO])
	}

}
