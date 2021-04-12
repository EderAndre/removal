package br.gov.rs.defensoria.removal.web.filter

import br.gov.rs.defensoria.removal.ITConfiguration
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

import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
class EditalBloqueadoFilterTest {

    @Autowired
    WebApplicationContext context

    MockMvc mvc

    @Before
    void setup() {
        mvc = webAppContextSetup(context)
                .build()
    }

    @Sql(scripts = [
        'deletes.sql',
        'editais.sql'
    ])
    @Test
    void 'testa filtro 01'() {

        int idEdital = 79
        String url = "/edital/${idEdital}/infos-edital"
        MvcResult result = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()

        def obj = new JsonSlurper().parse(result.response.getContentAsString(UTF_8).getBytes(), "UTF-8")
        assert obj.aberturaEdital == '2000-06-17T00:00:00.000'
        assert obj.assinaturaEmailEdital == null
        assert obj.bloqueado == false
        assert obj.descricaoEdital == 'Remoção de Defensores'
        assert obj.emailEnvioEdital == null
        assert obj.emailRespostaEdital == null
        assert obj.encerramentoEdital == '2099-08-17T00:00:00.000'
        assert obj.idEdital == 79
        assert obj.tipoEdital == 7

        idEdital = 99
        url = "/edital/${idEdital}/infos-edital"
        result = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()

        obj = new JsonSlurper().parse(result.response.getContentAsString(UTF_8).getBytes(), "UTF-8")
        assert obj.aberturaEdital == '2000-06-15T08:30:00.000'
        assert obj.assinaturaEmailEdital == null
        assert obj.bloqueado == false
        assert obj.descricaoEdital == 'Remoção de Analistas Processuais'
        assert obj.emailEnvioEdital == null
        assert obj.emailRespostaEdital == null
        assert obj.encerramentoEdital == '2099-06-24T18:00:00.000'
        assert obj.idEdital == 99
        assert obj.tipoEdital == 7
    }
}
