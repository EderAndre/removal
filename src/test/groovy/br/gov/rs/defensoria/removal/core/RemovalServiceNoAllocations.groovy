package br.gov.rs.defensoria.removal.core

import static br.gov.rs.defensoria.removal.core.test.office.Offices.*
import static br.gov.rs.defensoria.removal.core.test.office.Persons.*
import static org.junit.Assert.*

import org.junit.Test

import br.gov.rs.defensoria.removal.core.test.office.EditalCenarioBuilder
import br.gov.rs.defensoria.removal.core.test.office.EditalVerificaResultado
import br.gov.rs.defensoria.removal.domain.Office

class RemovalServiceNoAllocations {

	@Test
    void testGetNewAllocations() {
		given:
		EditalCenarioBuilder editalCenario = EditalCenarioBuilder.builder()
				.addCandidature(PEDRO, NOVO_HAMBURGO, [CAMPO_BOM, CAXIAS_DO_SUL, POUSO_NOVO])
				.addCandidature(MARIA, SAPIRANGA, [ALVORADA, SAPIRANGA, GRAVATAI])
				.addCandidature(PAULA, [SAPIRANGA, CAXIAS_DO_SUL, NOVO_HAMBURGO])
				.addCandidature(JOSE, CAMPO_BOM, [MORRO_REUTER, PORTO_ALEGRE, POUSO_NOVO])
				.addCandidature(CARLOS, PORTO_ALEGRE, [GRAVATAI, ALVORADA, SAPIRANGA])
				.addCandidature(JERICO, [CAMPO_BOM, DOIS_IRMAOS, SAPIRANGA])
				.addCandidature(NICLAUS, [PORTO_ALEGRE, MORRO_REUTER, ALVORADA])
				.setNotChoosableOffices([POUSO_NOVO, DOIS_IRMAOS])
				.build()

		when:
		RemovalService removalService = new RemovalService()
		List<Office> result = removalService.getNewAllocations(editalCenario.choosableOffices, editalCenario.allPersons)

		then:
		EditalVerificaResultado.builder(result)
				.expectWin(JERICO, CAMPO_BOM)
				.expectWin(PAULA, SAPIRANGA)
				.expectWin(PEDRO, CAXIAS_DO_SUL)
				.expectWin(NICLAUS, PORTO_ALEGRE)
				.expectWin(MARIA, ALVORADA)
				.expectWin(CARLOS, GRAVATAI)
				.expectWin(JOSE, MORRO_REUTER)
				.expectOfficeNoWinner([NOVO_HAMBURGO])
	}
}
