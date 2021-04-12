package br.gov.rs.defensoria.removal.web.controller


import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.api.Candidatura
import br.gov.rs.defensoria.removal.api.Captcha
import br.gov.rs.defensoria.removal.api.PedidoCandidatura
import br.gov.rs.defensoria.removal.api.Vaga
import org.codehaus.jackson.map.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.util.NestedServletException

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
class CandidaturaControllerTest {
    private static final String BASE_URI = "/edital/{editalId}/candidato/{candidatoId}/candidatura"

    @Autowired
    private WebApplicationContext webAppCtx

    private MockMvc mockMvc

    @Before
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppCtx).build()
    }

    @Test(expected = NestedServletException.class)
    @Sql([
        '/br/gov/rs/defensoria/removal/web/candidatura/cenariosetup/edital-basico-deletes-1.sql',
        '/br/gov/rs/defensoria/removal/web/candidatura/cenariosetup/unidades-1.sql',
        '/br/gov/rs/defensoria/removal/web/candidatura/cenariosetup/edital-basico-setup-1.sql']
    )
    void 'testa pedido de candidatura com captcha invalido'() {
        PedidoCandidatura pedido = new PedidoCandidatura(
                candidatura: dataProviderCandidaturaValida(),
                captcha: new Captcha(segredo: '0Ey8qWwtXmCdGDhW6MOmkQ==', codigo: 'avnp')
                )

        realizaPedidoCandidatura(pedido)
                .andExpect(status().isInternalServerError())
        //TODO: verificar se os registros foram salvos no banco
    }

    private ResultActions realizaPedidoCandidatura(PedidoCandidatura pedido) {
        return mockMvc.perform(post("${BASE_URI}/pedido/data-base/01-08-2015 19:00", 101, 12).content(asJsonString(pedido))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
    }

    private Candidatura dataProviderCandidaturaValida() {
        Candidatura candidatura = new Candidatura(edital: 101, candidato: 1, locais: new ArrayList<Vaga>())
        candidatura.locais.add(new Vaga(id: 4, ordem: 1))
        candidatura.locais.add(new Vaga(id: 8, ordem: 2))
        return candidatura
    }

    private static String asJsonString(final Object obj) {
        final ObjectMapper mapper = new ObjectMapper()
        return mapper.writeValueAsString(obj)
    }
}
