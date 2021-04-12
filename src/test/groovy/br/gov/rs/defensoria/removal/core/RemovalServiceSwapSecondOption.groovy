package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceSwapSecondOption {

	@Test
    void test() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, NOVO_HAMBURGO, [SAPIRANGA, PORTO_ALEGRE, CAMPO_BOM])
				.addCandidature(MARIA, CAXIAS_DO_SUL, [PORTO_ALEGRE, SAPIRANGA, GRAVATAI])
				.addCandidature(PAULA, ALVORADA, [GRAVATAI, SAPIRANGA, NOVO_HAMBURGO])
				.addCandidature(JOSE, SAPIRANGA, [MORRO_REUTER, NOVO_HAMBURGO, ALVORADA])
				.addCandidature(CARLOS, PORTO_ALEGRE, [SAPIRANGA, MORRO_REUTER, CAXIAS_DO_SUL])
				.addCandidature(JERICO, MORRO_REUTER, [POUSO_NOVO, DOIS_IRMAOS, SAPIRANGA])
				.setNotChoosableOffices([POUSO_NOVO, DOIS_IRMAOS])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(PEDRO, CAMPO_BOM)
				.expectWin(JERICO, SAPIRANGA)
				.expectWin(CARLOS, CAXIAS_DO_SUL)
				.expectWin(MARIA, PORTO_ALEGRE)
				.expectWin(PAULA, GRAVATAI)
				.expectWin(JOSE, MORRO_REUTER)
				.expectOfficeNoWinner([NOVO_HAMBURGO, ALVORADA])
	}

}
