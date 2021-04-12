package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceSwapInThree {

	@Test
    void test() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, ALVORADA, [SAPIRANGA, CAXIAS_DO_SUL, POUSO_NOVO])
				.addCandidature(MARIA, SAPIRANGA, [ALVORADA, SAPIRANGA, GRAVATAI])
				.addCandidature(PAULA, CAXIAS_DO_SUL, [MORRO_REUTER, PORTO_ALEGRE, DOIS_IRMAOS])
				.addCandidature(JOSE, CAMPO_BOM, [MORRO_REUTER, PORTO_ALEGRE, POUSO_NOVO])
				.addCandidature(CARLOS, PORTO_ALEGRE, [CAMPO_BOM, ALVORADA, SAPIRANGA])
				.addCandidature(JERICO, MORRO_REUTER, [PORTO_ALEGRE, DOIS_IRMAOS, SAPIRANGA])
				.addCandidature(NICLAUS, GRAVATAI, [POUSO_NOVO, NOVO_HAMBURGO, ALVORADA])
				.setNotChoosableOffices([POUSO_NOVO, DOIS_IRMAOS])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(CARLOS, CAMPO_BOM)
				.expectWin(PEDRO, SAPIRANGA)
				.expectWin(NICLAUS, NOVO_HAMBURGO)
				.expectWin(JERICO, PORTO_ALEGRE)
				.expectWin(MARIA, ALVORADA)
				.expectWin(JOSE, MORRO_REUTER)
				.expectOfficeNoWinner([CAXIAS_DO_SUL, GRAVATAI])
	}

}
