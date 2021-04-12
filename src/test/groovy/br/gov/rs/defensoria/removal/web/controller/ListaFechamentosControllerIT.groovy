package br.gov.rs.defensoria.removal.web.controller

import br.gov.rs.defensoria.oauth.test.WithDpeOauthUser
import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.web.filter.EditalBloqueadoFilter
import static org.hamcrest.Matchers.is
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
class ListaFechamentosControllerIT {

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
	    'fechamentos.sql'
	])
	@WithDpeOauthUser(roles = [DEV])
	void deveListarOsFechamentosEmOrdemCrescenteDeId() {
		String url = "/edital/99/fechamentos/lista"
		Integer i = 0

		mvc.perform(get(url))
				.andExpect(jsonPath("\$[${i}].idFechamento", is(1)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(2)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(3)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(4)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(5)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(6)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(7)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(8)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(9)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(10)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(11)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(12)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(13)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(14)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(15)))
				.andExpect(jsonPath("\$[${++i}].idFechamento", is(16)))
	}
}
