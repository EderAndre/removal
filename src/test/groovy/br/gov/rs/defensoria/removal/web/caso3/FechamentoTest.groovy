package br.gov.rs.defensoria.removal.web.caso3

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
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static java.nio.charset.StandardCharsets.UTF_8
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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
        'vagasEdital.sql',
        'candidaturas.sql',
        'fechamentos.sql'
    ])
    @WithDpeOauthUser(roles = [UaaRole.DEV])
    void testFechamento() {

        int fechamento = 27
        String url = "/fechamento/${fechamento}/true/true"
        mvc.perform(get(url))

        List<SnapResultadosEntity> snapSRs = snapResultadosRepository.findByCodFechamento(fechamento)

        assert 19 == snapSRs.size()
        assert 3 == snapSRs.findAll {it.matricula != 0}.size()

        def expectedResult = getFromJSON('snap_resultados.json')
        expectedResult.each { er->
            SnapResultadosEntity resultado = snapSRs.find {it.codLocal == er.cod_local}
            if(resultado){
                assert resultado.matricula == er.matricula
                assert resultado.ordemGanha == er.ordem_ganha
                assert resultado.codFechamento == er.cod_fechamento
            }
        }

        int idEdital = 100
        url = "/edital/${idEdital}/obj-constroi-pedido/"
        MvcResult objPedido = mvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()

        def obj = new JsonSlurper().parse(objPedido.response.getContentAsString(UTF_8).getBytes(), "UTF-8")
        expectedResult = getFromJSON('obj-constroi-pedido.json')

        assert obj.idCandidato == expectedResult.idCandidato
        assert obj.idEdital == expectedResult.idEdital
        assert obj.vagas.size() == expectedResult.vagas.size()
        obj.vagas.each {
            def vaga = expectedResult.vagas.find { er ->
                er.id == it.id
            }
            assert vaga.ordem == it.ordem
            assert vaga.nomeExibicao == it.nomeExibicao
            assert vaga.escolhida == it.escolhida
            assert vaga.selecionada == it.selecionada
            assert vaga.tipoVaga == it.tipoVaga
            assert vaga.info == it.info
        }
    }

    def getFromJSON(String jsonFile) {
        return (new JsonSlurper()).parseText(this.getClass().getResource(jsonFile).getText("UTF-8")).result
    }
}
