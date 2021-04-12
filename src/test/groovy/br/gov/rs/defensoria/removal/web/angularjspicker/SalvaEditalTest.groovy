package br.gov.rs.defensoria.removal.web.angularjspicker

import br.gov.rs.defensoria.oauth.test.WithDpeOauthUser
import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.api.ObjInfosEdital
import br.gov.rs.defensoria.removal.core.auth.UaaRole
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.web.angularjspicker.test.salvaedital.SalvaEditalVerificaResultado
import br.gov.rs.defensoria.removal.web.filter.EditalBloqueadoFilter
import org.joda.time.LocalDateTime
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
class SalvaEditalTest {

    @Autowired
    WebApplicationContext context

    MockMvc mvc

    @Autowired
    EditalBloqueadoFilter editalBloqueadoFilter

    @Autowired
    EditaisRepository editaisRepository

    @Before
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(editalBloqueadoFilter)
                .build()

    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editais.sql'
    ])
    @WithDpeOauthUser(roles = [UaaRole.DEV])
    void testSalvaEdital() {

        int idEdital = 1

        ObjInfosEdital oie = new ObjInfosEdital(idEdital: idEdital,
        aberturaEdital: 'Wed Feb 24 2016 02:30:00 GMT-0300 (Hora oficial do Brasil)',
        encerramentoEdital: 'Sat Feb 27 2016 04:15:00 GMT-0200 (Hora oficial do Brasil)',
        limitePrimeiraCandidatura: 'Sat Mar 26 2016 04:00:00 GMT-0300 (Hora oficial do Brasil)',
        dataApenasDesistenciasPermitidas: 'Sat Feb 27 2016 04:00:00 GMT-0300 (Hora oficial do Brasil)')

        LocalDateTime abertura = new LocalDateTime(2016, 2, 24, 2, 30)
        LocalDateTime encerramento = new LocalDateTime(2016, 2, 27, 4, 15)
        LocalDateTime limite = new LocalDateTime(2016, 3, 26, 4, 0)
        LocalDateTime apenasDesistencias = new LocalDateTime(2016, 2, 27, 4, 0)

        SalvaEditalVerificaResultado.builder(mvc, idEdital, oie, abertura, encerramento, limite, apenasDesistencias, editaisRepository).verifica()
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editais.sql'
    ])
    @WithDpeOauthUser(roles = [UaaRole.DEV])
    void testSalvaEdital02() {

        int idEdital = 1

        ObjInfosEdital oie = new ObjInfosEdital(idEdital: idEdital,
        aberturaEdital: '2020-02-24T02:30:00.000',
        encerramentoEdital: '2020-02-27T04:15:00.000',
        limitePrimeiraCandidatura: '2020-03-26T04:00:00.000',
        dataApenasDesistenciasPermitidas: '2020-02-26T04:00:00.000')

        LocalDateTime abertura = new LocalDateTime(2020, 2, 24, 2, 30)
        LocalDateTime encerramento = new LocalDateTime(2020, 2, 27, 4, 15)
        LocalDateTime limite = new LocalDateTime(2020, 3, 26, 4, 0)
        LocalDateTime apenasDesistencias = new LocalDateTime(2020, 2, 26, 4, 0)

        SalvaEditalVerificaResultado.builder(mvc, idEdital, oie, abertura, encerramento, limite, apenasDesistencias, editaisRepository).verifica()
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editais.sql'
    ])
    @WithDpeOauthUser(roles = [UaaRole.DEV])
    void testSalvaEdital03() {

        int idEdital = 1

        ObjInfosEdital oie = new ObjInfosEdital(idEdital: idEdital,
        aberturaEdital: '3020-02-24T02:30:00.000Z',
        encerramentoEdital: '3020-02-27T04:15:00.000Z',
        limitePrimeiraCandidatura: '3020-03-26T04:00:00.000Z',
        dataApenasDesistenciasPermitidas: '3020-02-26T04:00:00.000Z')

        LocalDateTime abertura = new LocalDateTime(3020, 2, 24, 2, 30)
        LocalDateTime encerramento = new LocalDateTime(3020, 2, 27, 4, 15)
        LocalDateTime limite = new LocalDateTime(3020, 3, 26, 4, 0)
        LocalDateTime apenasDesistencias = new LocalDateTime(3020, 2, 26, 4, 0)

        SalvaEditalVerificaResultado.builder(mvc, idEdital, oie, abertura, encerramento, limite, apenasDesistencias, editaisRepository).verifica()
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editais.sql'
    ])
    @WithDpeOauthUser(roles = [UaaRole.DEV])
    void testSalvaEdital04() {

        int idEdital = 1

        ObjInfosEdital oie = new ObjInfosEdital(idEdital: idEdital,
        aberturaEdital: '2000-02-24T02:30',
        encerramentoEdital: '2000-02-27T04:15',
        limitePrimeiraCandidatura: '2000-03-26T04:00',
        dataApenasDesistenciasPermitidas: '2000-02-26T04:00')

        LocalDateTime abertura = new LocalDateTime(2000, 2, 24, 2, 30)
        LocalDateTime encerramento = new LocalDateTime(2000, 2, 27, 4, 15)
        LocalDateTime limite = new LocalDateTime(2000, 3, 26, 4, 0)
        LocalDateTime apenasDesistencias = new LocalDateTime(2000, 2, 26, 4, 0)

        SalvaEditalVerificaResultado.builder(mvc, idEdital, oie, abertura, encerramento, limite, apenasDesistencias, editaisRepository).verifica()
    }


    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editais.sql'
    ])
    @WithDpeOauthUser(roles = [UaaRole.DEV])
    void testSalvaEdital05() {

        int idEdital = 1

        ObjInfosEdital oie = new ObjInfosEdital(idEdital: idEdital,
        aberturaEdital: '2000-02-24T02:30',
        encerramentoEdital: '2000-02-27T04:15',
        limitePrimeiraCandidatura: '2000-03-26T04:00')

        LocalDateTime abertura = new LocalDateTime(2000, 2, 24, 2, 30)
        LocalDateTime encerramento = new LocalDateTime(2000, 2, 27, 4, 15)
        LocalDateTime limite = new LocalDateTime(2000, 3, 26, 4, 0)
        LocalDateTime apenasDesistencias = null

        SalvaEditalVerificaResultado.builder(mvc, idEdital, oie, abertura, encerramento, limite, apenasDesistencias, editaisRepository).verifica()
    }
}
