package br.gov.rs.defensoria.removal.maestro.modo

import org.springframework.boot.test.context.SpringBootTest

import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertTrue

import org.joda.time.LocalDateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import br.gov.rs.defensoria.removal.api.enumerator.ModoEdital
import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.maestro.helper.CurrentTimeHelper

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles('IT')
@SpringBootTest
class LimitePrimeiraCandidaturaTest {

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
        'editaisFuncoes4.sql'
    ])
    void limitePrimeiraCandidaturaNulo() {
        assertFalse(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.LIMITE_PRIMEIRA_CANDIDATURA))
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes5.sql'
    ])
    void dataAtualAntesLimitePrimeiraCandidatura() {
        LocalDateTime current = new LocalDateTime(2016, 2, 14, 0, 0)
        currentTimeHelper.setCurrentTime(current)

        assertFalse(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.LIMITE_PRIMEIRA_CANDIDATURA))
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes5.sql'
    ])
    void dataAtualIgualLimitePrimeiraCandidatura() {
        LocalDateTime current = new LocalDateTime(2016, 2, 15, 18, 0)
        currentTimeHelper.setCurrentTime(current)

        assertTrue(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.LIMITE_PRIMEIRA_CANDIDATURA))
    }

    @Test
    @Sql(scripts = [
        'deletes.sql',
        'editaisFuncoes5.sql'
    ])
    void dataAtualPosteriorLimitePrimeiraCandidatura() {
        LocalDateTime current = new LocalDateTime(2016, 2, 15, 20, 0)
        currentTimeHelper.setCurrentTime(current)

        assertTrue(modoEditalManager.hasModoAtivo(ID_EDITAL, ModoEdital.LIMITE_PRIMEIRA_CANDIDATURA))
    }
}
