package br.gov.rs.defensoria.removal.maestro.modo

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital
import br.gov.rs.defensoria.removal.maestro.helper.CurrentTimeHelper
import org.joda.time.LocalDateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles('IT')
@SpringBootTest
class SomenteDesistenciaTest {

    private final Integer ID_EDITAL = 1

    @Autowired
    ModoEditalManager modoEditalManager

    @Autowired
    CurrentTimeHelper currentTimeHelper

    LocalDateTime realTime

    @Before
    void setUp() {
        realTime = LocalDateTime.now()
    }

    @After
    void tearDown() {
        currentTimeHelper.setCurrentTime(realTime)
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes.sql'
    ])
    void dataApenasDesistenciasNula() {
        assertFalse(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.SOMENTE_DESISTENCIAS))
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes2.sql'
    ])
    void dataAtualAntesDataSomenteDesistencias() {
        LocalDateTime current = new LocalDateTime(2016, 3, 30, 0, 0)
        currentTimeHelper.setCurrentTime(current)

        assertFalse(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.SOMENTE_DESISTENCIAS))
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes2.sql'
    ])
    void dataAtualIgualDataSomenteDesistencias() {
        LocalDateTime current = new LocalDateTime(2016, 4, 1, 9, 0)
        currentTimeHelper.setCurrentTime(current)

        assertTrue(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.SOMENTE_DESISTENCIAS))
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes2.sql'
    ])
    void dataAtualPassouDataSomenteDesistencias() {
        LocalDateTime current = new LocalDateTime(2016, 4, 1, 13, 0)
        currentTimeHelper.setCurrentTime(current)

        assertTrue(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.SOMENTE_DESISTENCIAS))
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes3.sql'
    ])
    void dataSomenteDesistenciasAntesAberturaEdital() {
        assertTrue(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.SOMENTE_DESISTENCIAS))
    }
}
