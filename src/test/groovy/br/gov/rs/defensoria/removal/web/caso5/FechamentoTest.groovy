package br.gov.rs.defensoria.removal.web.caso5

import br.gov.rs.defensoria.oauth.test.WithDpeOauthUser
import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.core.auth.UaaRole
import br.gov.rs.defensoria.removal.repository.SnapResultadosRepository
import br.gov.rs.defensoria.removal.repository.entity.SnapResultadosEntity
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
class FechamentoTest {

	@Autowired
	SnapResultadosRepository snapResultadosRepository

	@Autowired
	WebApplicationContext context

	MockMvc mvc

	@Autowired
	EditalBloqueadoFilter editalBloqueadoFilter

	@Before
	void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilters(editalBloqueadoFilter)
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
		'vagasEdital.sql',
		'candidaturas.sql',
		'fechamentos.sql'
	])
	@WithDpeOauthUser(roles = [UaaRole.DEV])
	void testFechamento() {

		int fechamento = 2
		String url = "/fechamento/${fechamento}/true/true"
		mvc.perform(get(url))

		List<SnapResultadosEntity> snapSRs = snapResultadosRepository.findByCodFechamento(fechamento)

		assert 26 == snapSRs.size()
		assert 4 == snapSRs.findAll {it.matricula != 0}.size()

		def expectedResult = getFromJSON('snap_resultados.json')
		expectedResult.each { er->
			SnapResultadosEntity resultado = snapSRs.find {it.codLocal == er.cod_local}
			if(resultado){
				assert resultado.matricula == er.matricula
				assert resultado.ordemGanha == er.ordem_ganha
				assert resultado.codFechamento == er.cod_fechamento
			}
		}
	}

	def getFromJSON(String jsonFile) {
		return (new JsonSlurper()).parseText(this.getClass().getResource(jsonFile).getText("UTF-8")).result
	}
}
