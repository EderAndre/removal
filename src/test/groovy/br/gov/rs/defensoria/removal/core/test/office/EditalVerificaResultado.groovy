package br.gov.rs.defensoria.removal.core.test.office

import static org.junit.Assert.*
import br.gov.rs.defensoria.removal.domain.Office

class EditalVerificaResultado {
	List<Office> resultOffices

    EditalVerificaResultado(List<Office> offices) {
		resultOffices = offices
	}

    static EditalVerificaResultado builder(List<Office> offices) {
		return new EditalVerificaResultado(offices)
	}

    EditalVerificaResultado expectWin(Persons winner, Offices officeWin) {
		Office office = resultOffices.find { it.id == officeWin.id }
		assertEquals(office.pretender.id, winner.id)
		return this
	}

    EditalVerificaResultado expectWinNothing(Persons loser) {
		assertEquals(0, resultOffices.count { office -> office.pretender?.id == loser.id})
		return this
	}

    EditalVerificaResultado expectOfficeNoWinner(List<Offices> offices) {
		offices.each { o ->
			Office office = resultOffices.find { it.id == o.id }
			assertEquals(null, office.pretender)
		}
	}
}
