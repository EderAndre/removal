package br.gov.rs.defensoria.removal.web.filter

import static org.junit.Assert.*

import org.junit.Test

class ExtraiURIFilterTest {

	@Test
    void 'extrai editalId da URI'() {
		def filter = new EditalBloqueadoFilter()

		dataProviderURI().each { dp ->
			assert filter.extraiEditalIdFromURI(dp.uri) == dp.editalId
		}
	}

	def dataProviderURI() {
		return [
			[uri: '/edital/101/info', editalId: 101, matches: true],
			[uri: '/edital/101/', editalId: 101, matches: true],
			[uri: '/edital/101', editalId: 101, matches: true],
			[uri: '/horaatual', editalId: null, matches: false]
		]
	}
}
