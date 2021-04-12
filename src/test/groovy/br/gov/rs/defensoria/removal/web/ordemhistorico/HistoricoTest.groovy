package br.gov.rs.defensoria.removal.web.ordemhistorico

import br.gov.rs.defensoria.oauth.test.WithDpeOauthUser
import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.core.auth.UaaRole
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
import org.springframework.test.web.servlet.MvcResult
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
class HistoricoTest {

    @Autowired
    WebApplicationContext context

    MockMvc mvc

    @Autowired
    EditalBloqueadoFilter editalBloqueadoFilter

    @Before
    void setup() {
        mvc = webAppContextSetup(context)
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
        'candidaturas.sql'
    ])
    @WithDpeOauthUser(roles = [UaaRole.DEV])
    void testOrdemHistorico() {

        int idEdital = 1
        int idCandidato = 369
        String url = "/edital/${idEdital}/obj-historico/candidato/${idCandidato}"
        MvcResult objPedido = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()

        def obj = new JsonSlurper().parse(objPedido.response.contentAsString.getBytes(), "UTF-8")
        def expectedResult = getFromJSON('obj-historico.json')

        assert obj == expectedResult
    }

    def getFromJSON(String jsonFile) {
        return (new JsonSlurper()).parseText(this.getClass().getResource(jsonFile).getText("UTF-8")).result
    }
}
