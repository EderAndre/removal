package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceSwapInThreeIgnoreAntiquity {

	@Test
    void test() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, ALVORADA, [SAPIRANGA, CAXIAS_DO_SUL, POUSO_NOVO])
				.addCandidature(MARIA, SAPIRANGA, [ALVORADA, MORRO_REUTER, GRAVATAI])
				.addCandidature(PAULA, CAXIAS_DO_SUL, [MORRO_REUTER, PORTO_ALEGRE, DOIS_IRMAOS])
				.addCandidature(JOSE, CAMPO_BOM, [SAPIRANGA, PORTO_ALEGRE, POUSO_NOVO])
				.appendChoosables([NOVO_HAMBURGO])
				.setNotChoosableOffices([POUSO_NOVO, DOIS_IRMAOS])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(JOSE, SAPIRANGA)
				.expectWin(PEDRO, CAXIAS_DO_SUL)
				.expectWin(MARIA, ALVORADA)
				.expectWin(PAULA, MORRO_REUTER)
				.expectOfficeNoWinner([CAMPO_BOM, NOVO_HAMBURGO, PORTO_ALEGRE, GRAVATAI])
	}

}
