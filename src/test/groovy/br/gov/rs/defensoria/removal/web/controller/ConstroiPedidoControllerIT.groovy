package br.gov.rs.defensoria.removal.web.controller

import br.gov.rs.defensoria.oauth.test.WithDpeOauthUser
import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.web.filter.EditalBloqueadoFilter
import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext

import static br.gov.rs.defensoria.removal.core.auth.UaaRole.DEV
import static org.junit.Assert.assertEquals
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
class ConstroiPedidoControllerIT {

	@Autowired
	WebApplicationContext context

	MockMvc mvc

	@Autowired
	EditalBloqueadoFilter editalBloqueadoFilter

	@Before
	void setup() {
		mvc = webAppContextSetup(context)
				.addFilter(editalBloqueadoFilter)
				.build()
	}

	@Test
	@Sql(scripts = [
		'deletes.sql',
		'editaisFuncoes.sql',
		'servidores.sql',
		'comarcas.sql',
		'unidades.sql',
		'candidatos.sql',
		'vagasEdital.sql'
	])
	@WithDpeOauthUser(roles = [DEV])
	void deveListarTodasAsVagasIndependentementeDaCorEMesmoQueNaoTenhaTidoFechamento() {
		int editalId = 99
		String url = "/edital/${editalId}/obj-constroi-pedido/"

		assertData(url, 'expected-oferta.json')
	}

	@Test
	@Sql(scripts = [
			'deletes.sql',
			'editaisFuncoes.sql',
			'servidores.sql',
			'comarcas.sql',
			'unidades.sql',
			'candidatos.sql',
			'vagasEdital.sql'
	])
	@WithDpeOauthUser(roles = [DEV])
	void deveListarTodasAsVagasIndependentementeDaCorEMesmoQueNaoTenhaTidoFechamento02() {
		int editalId = 99
		int candidatoId = 2
		String url = "/edital/$editalId/obj-constroi-pedido/candidato/$candidatoId"

		assertData(url, 'expected-oferta-02.json')
	}

	private void assertData(String url, String fileName) {

		byte[] oferta = mvc.perform(get(url))
				.andReturn().response.contentAsString.getBytes()

		def actual = new JsonSlurper().parse(oferta, "UTF-8")
		def expected = getFromJSON(fileName)

		assertEquals(expected, actual)
	}

	def getFromJSON(String jsonFile) {
		return (new JsonSlurper()).parseText(this.getClass().getResource(jsonFile).getText("UTF-8")).result
	}
}
