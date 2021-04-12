package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.maestro.FechamentoReports
import br.gov.rs.defensoria.removal.maestro.FechamentoService
import org.joda.time.DateTime
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

import static org.joda.time.DateTimeUtils.currentMillisFixed
import static org.joda.time.DateTimeUtils.setCurrentMillisSystem
import static org.mockito.ArgumentMatchers.anyInt
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify

@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(classes = ITConfiguration)
@ActiveProfiles('IT')
@SpringBootTest
@Transactional
@Sql
class FechamentoExecutorTest {

    @Autowired
    private FechamentoExecutor executor

    @MockBean
    private FechamentoService fechamentoService

    @MockBean
    private FechamentoReports reportService

    @After
    void teardown() {
        setCurrentMillisSystem()
    }

    @Test
    void deveRealizarFechamentoEEmitirRelatorioParaUmEditalVigente() {
        setDate(11, 30)

        executor.execute()

        assertInvocations(1)
    }

    @Test
    void deveRealizarFechamentoEEmitirRelatorioParaMaisDeUmEditalVigente() {
        setDate(11, 26)

        executor.execute()

        assertInvocations(3)
    }

    @Test
    void naoDeveRealizarFechamentoNemEmitirRelatorioSemExistirEditalVigente() {
        setDate(12, 1)

        executor.execute()

        assertInvocations(0)
    }

    private void setDate(int month, int day) {
        setCurrentMillisFixed(new DateTime(1999, month, day, 0, 0).millis)
    }

    private void assertInvocations(int executions) {
        verify(fechamentoService, times(executions)).computeFechamento(eq(true), anyInt(), eq(true), eq(true))
        verify(reportService, times(executions)).generateReports(anyInt())
    }
}
