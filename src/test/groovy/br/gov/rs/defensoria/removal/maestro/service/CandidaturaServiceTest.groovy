package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.api.Candidatura
import br.gov.rs.defensoria.removal.api.Vaga
import br.gov.rs.defensoria.removal.repository.CandidaturasRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles("IT")
@SpringBootTest
class CandidaturaServiceTest {

    @Autowired
    CandidaturaService service

    @Autowired
    CandidaturasRepository candidaturasRepository

    @Test
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'testa persistencia de candidatura com 3 vagas solicitadas'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:2))
        locais.add(new Vaga(id: 8, ordem:1))
        locais.add(new Vaga(id: 27, ordem:3))
        def candidatura1 = new Candidatura(edital: 101, candidato: 1, locais: locais)

        assert service.salvaCandidatura(candidatura1) == true
        assert candidaturasRepository.count() == 3
    }

    @Test(expected = RuntimeException.class)
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'joga excessao quando ordem da lista de vagas da candidatura não é sequencial'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 8, ordem:2))
        locais.add(new Vaga(id: 27, ordem:5))
        def candidatura1 = new Candidatura(edital: 101, candidato: 1, locais: locais)

        assert service.salvaCandidatura(candidatura1) == true
        assert candidaturasRepository.count() == 0
    }

    @Test
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'testa persistencia de candidatura vazia'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        def candidatura1 = new Candidatura(edital: 101, candidato: 1, locais: locais)

        assert service.salvaCandidatura(candidatura1) == true
        assert candidaturasRepository.count() == 1
    }

    @Test(expected = RuntimeException.class)
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void jogaExcecaoQuandoVagaNaoEEncontradaNoEdital() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 8, ordem:2))
        locais.add(new Vaga(id: 279999, ordem:3))
        def candidatura1 = new Candidatura(edital: 101, candidato: 1, locais: locais)

        assert service.salvaCandidatura(candidatura1) == true
    }

    @Test(expected = RuntimeException.class)
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'joga excessao caso o candidato informado não existe'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 8, ordem:2))
        locais.add(new Vaga(id: 27, ordem:3))
        def candidatura1 = new Candidatura(edital: 101, candidato: 99999, locais: locais)

        service.salvaCandidatura(candidatura1)
        assert candidaturasRepository.count() == 0
    }

    @Test(expected = RuntimeException.class)
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'joga excessao caso o candidato informado não pertence ao edital informado na candidatura'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 8, ordem:2))
        locais.add(new Vaga(id: 27, ordem:3))
        def candidatura1 = new Candidatura(edital: 101, candidato: 2, locais: locais)

        service.salvaCandidatura(candidatura1)
        assert candidaturasRepository.count() == 0
    }

    @Test
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'aborta persistencia da candidatura quando a mesma não é concluida com sucesso'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 8, ordem:2))
        locais.add(new Vaga(id: 279999, ordem:3))
        def candidatura1 = new Candidatura(edital: 101, candidato: 1, locais: locais)

        try {
            service.salvaCandidatura(candidatura1)
        }catch(Exception ex) {}
        assert candidaturasRepository.count() == 0
    }

    @Test(expected = RuntimeException.class)
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'não permite que o candidato se candidate a sua atual lotação'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 7, ordem:2)) //lotacao atual do candidato 1
        def candidatura = new Candidatura(edital: 101, candidato: 1, locais: locais)
        service.salvaCandidatura(candidatura)
        assert candidaturasRepository.count() == 0
    }

    @Test(expected = RuntimeException.class)
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'joga excessão quando a candidatura possui locais repetidos'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 8, ordem:2))
        locais.add(new Vaga(id: 8, ordem:3))
        def candidatura = new Candidatura(edital: 101, candidato: 1, locais: locais)
        service.salvaCandidatura(candidatura)
        assert candidaturasRepository.count() == 0
    }

    @Test
    void 'testa o impedimento de candidatura com vagas repetidas'() {
        List<Vaga> locaisRepetidos = new ArrayList<Vaga>()
        locaisRepetidos.add(new Vaga(id: 4, ordem:1))
        locaisRepetidos.add(new Vaga(id: 8, ordem:2))
        locaisRepetidos.add(new Vaga(id: 8, ordem:3))

        assert service.candidaturaPossuiVagasRepetidas(locaisRepetidos) == true

        List<Vaga> locaisNaoRepetidos = new ArrayList<Vaga>()
        locaisNaoRepetidos.add(new Vaga(id: 4, ordem:1))
        locaisNaoRepetidos.add(new Vaga(id: 8, ordem:2))

        assert service.candidaturaPossuiVagasRepetidas(locaisNaoRepetidos) == false
    }

    @Test
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'testa o impedimento de vaga para o candidato'() {
        Integer candidatoId = 1
        Integer vagaImpedidaId = 12
        Integer vagaNaoImpedidaId = 8
        assert service.vagaImpedidaParaCandidato(vagaImpedidaId, candidatoId) == true
        assert service.vagaImpedidaParaCandidato(vagaNaoImpedidaId, candidatoId) == false
    }

    @Test(expected = RuntimeException.class)
    @Sql(['/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-deletes-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/unidades-1.sql', '/br/gov/rs/defensoria/removal/maestro/cenariosetup/edital-basico-setup-1.sql'])
    void 'aborta candidatura quando alguma vaga da candidatura está impedida para o candidato'() {
        assert candidaturasRepository.count() == 0

        List<Vaga> locais = new ArrayList<Vaga>()
        locais.add(new Vaga(id: 4, ordem:1))
        locais.add(new Vaga(id: 8, ordem:2))
        locais.add(new Vaga(id: 12, ordem:3))
        def candidatura = new Candidatura(edital: 101, candidato: 1, locais: locais)
        service.salvaCandidatura(candidatura)
        assert candidaturasRepository.count() == 0
    }
}


// json = [{edital:99, candidato: 101, candidaturas: [{local:1, ordem: 1}, {local:219, ordem: 2}]}]
