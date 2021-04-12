package br.gov.rs.defensoria.removal.maestro.service.restricoes

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.api.Candidatura
import br.gov.rs.defensoria.removal.api.DadosRealizacaoCandidatura
import br.gov.rs.defensoria.removal.api.PedidoCandidatura
import br.gov.rs.defensoria.removal.api.Vaga
import br.gov.rs.defensoria.removal.maestro.service.SomenteDesistenciasService
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles('IT')
@SpringBootTest
class RestricoesCandidaturasServiceTest {

    @Autowired
    SomenteDesistenciasService somenteDesistenciasService

    List<Vaga> locais = new ArrayList<>()

    Candidatura candidatura

    PedidoCandidatura pedido = new PedidoCandidatura()

    DadosRealizacaoCandidatura dadosRealizacaoCandidatura = new DadosRealizacaoCandidatura()

    @Before
    void setUp() {
        candidatura = new Candidatura(candidato: 369,
        edital: 1)

        dadosRealizacaoCandidatura.idEdital = 1
        dadosRealizacaoCandidatura.idCandidato = 369
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void semAlteracoes() {
        locais.add(new Vaga(id: 106, ordem: 1))
        locais.add(new Vaga(id: 1, ordem: 2))
        locais.add(new Vaga(id: 117, ordem: 3))
        locais.add(new Vaga(id: 85, ordem: 4))
        locais.add(new Vaga(id: 104, ordem: 5))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertTrue(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void apenasDesistencias() {
        locais.add(new Vaga(id: 106, ordem: 1))
        locais.add(new Vaga(id: 85, ordem: 2))
        locais.add(new Vaga(id: 104, ordem: 3))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura)
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void apenasDesistencias02() {
        locais.add(new Vaga(id: 117, ordem: 1))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertTrue(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void apenasDesistenciasDeixandoSoOPrimeiro() {
        locais.add(new Vaga(id: 106, ordem: 1))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertTrue(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void apenasDesistenciasDeixandoSoOUltimo() {
        locais.add(new Vaga(id: 104, ordem: 1))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertTrue(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void desistiuDeTudo() {

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertTrue(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void trocaEntreOsDoisPrimeiros() {
        locais.add(new Vaga(id: 1, ordem: 1))
        locais.add(new Vaga(id: 106, ordem: 2))
        locais.add(new Vaga(id: 117, ordem: 3))
        locais.add(new Vaga(id: 85, ordem: 4))
        locais.add(new Vaga(id: 104, ordem: 5))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void trocaEntreOsDoisUltimos() {
        locais.add(new Vaga(id: 106, ordem: 1))
        locais.add(new Vaga(id: 1, ordem: 2))
        locais.add(new Vaga(id: 117, ordem: 3))
        locais.add(new Vaga(id: 104, ordem: 4))
        locais.add(new Vaga(id: 85, ordem: 5))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void trocaEntrePrimeiroEUltimo() {
        locais.add(new Vaga(id: 104, ordem: 1))
        locais.add(new Vaga(id: 1, ordem: 2))
        locais.add(new Vaga(id: 117, ordem: 3))
        locais.add(new Vaga(id: 85, ordem: 4))
        locais.add(new Vaga(id: 106, ordem: 5))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void trocaEntreElementosDoMeio() {
        locais.add(new Vaga(id: 106, ordem: 1))
        locais.add(new Vaga(id: 85, ordem: 2))
        locais.add(new Vaga(id: 117, ordem: 3))
        locais.add(new Vaga(id: 1, ordem: 4))
        locais.add(new Vaga(id: 104, ordem: 5))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void trocaEntreElementosDoMeio02() {
        locais.add(new Vaga(id: 106, ordem: 1))
        locais.add(new Vaga(id: 1, ordem: 2))
        locais.add(new Vaga(id: 85, ordem: 3))
        locais.add(new Vaga(id: 117, ordem: 4))
        locais.add(new Vaga(id: 104, ordem: 5))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void trocaEntreElementosDoMeio03() {
        locais.add(new Vaga(id: 1, ordem: 1))
        locais.add(new Vaga(id: 106, ordem: 2))
        locais.add(new Vaga(id: 85, ordem: 3))
        locais.add(new Vaga(id: 117, ordem: 4))
        locais.add(new Vaga(id: 104, ordem: 5))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void insercaoNoInicio() {
        locais.add(new Vaga(id: 90, ordem: 1))
        locais.add(new Vaga(id: 106, ordem: 2))
        locais.add(new Vaga(id: 1, ordem: 3))
        locais.add(new Vaga(id: 117, ordem: 4))
        locais.add(new Vaga(id: 85, ordem: 5))
        locais.add(new Vaga(id: 104, ordem: 6))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void insercaoNoMeio() {
        locais.add(new Vaga(id: 106, ordem: 1))
        locais.add(new Vaga(id: 1, ordem: 2))
        locais.add(new Vaga(id: 117, ordem: 3))
        locais.add(new Vaga(id: 90, ordem: 4))
        locais.add(new Vaga(id: 85, ordem: 5))
        locais.add(new Vaga(id: 104, ordem: 6))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void desistenciaComMudancaEntreOsDoisUltimos() {
        locais.add(new Vaga(id: 106, ordem: 1))
        locais.add(new Vaga(id: 1, ordem: 2))
        locais.add(new Vaga(id: 104, ordem: 3))
        locais.add(new Vaga(id: 85, ordem: 4))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void desistenciaComMudancaEntreOsDoisPrimeiros() {
        locais.add(new Vaga(id: 1, ordem: 1))
        locais.add(new Vaga(id: 106, ordem: 2))
        locais.add(new Vaga(id: 117, ordem: 3))
        locais.add(new Vaga(id: 104, ordem: 4))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
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
        'fechamentos.sql',
        'snapLocais.sql',
        'snapPretensoes.sql',
        'snapResultados.sql'
    ])
    void desistenciaComMudancas() {
        locais.add(new Vaga(id: 117, ordem: 1))
        locais.add(new Vaga(id: 104, ordem: 2))
        locais.add(new Vaga(id: 1, ordem: 3))

        candidatura.locais = locais

        pedido.candidatura = candidatura

        dadosRealizacaoCandidatura.pedido = pedido

        assertFalse(somenteDesistenciasService.isMesmaOrdem(dadosRealizacaoCandidatura))
    }
}
