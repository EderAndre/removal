package br.gov.rs.defensoria.removal.maestro.caso2

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.maestro.CreateSnapshotService
import br.gov.rs.defensoria.removal.repository.SnapLocaisRepository
import br.gov.rs.defensoria.removal.repository.entity.SnapLocaisEntity
import groovy.json.JsonSlurper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static org.junit.Assert.assertEquals

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles('IT')
@SpringBootTest
class SnapInLocaisDisponiveisIT {

	@Autowired
	CreateSnapshotService createSnapshotService

	@Autowired
	SnapLocaisRepository snapLocaisRepository


	@Test
	@Sql(scripts = [
		'deletes.sql',
		'editaisFuncoesAmbos.sql',
		'servidoresAnalistas.sql',
		'servidoresTecnicos.sql',
		'comarcasAmbos.sql',
		'unidadesAmbos.sql',
		'candidatosAnalistas.sql',
		'candidatosTecnicos.sql',
		'vagasEditalAnalistas.sql',
		'vagasEditalTecnicos.sql',
		'candidaturasAnalistas.sql',
		'candidaturasTecnicos.sql',
		'fechamentosAmbos.sql'
	])
	void snapInLocaisDisponiveis() {
		createSnapshotService.createSnapLocaisDisponiveis(22)

		List<SnapLocaisEntity> savedSLs = snapLocaisRepository.findAll()
		assertEquals(108, savedSLs.size())

		def expectedResult = getFromJson('snap_locais.json')

		expectedResult.each { er->
			SnapLocaisEntity local = savedSLs.find {it.idLocal == er.id_local}
			if(local){
				assert local.idFechamento == er.id_fechamento
				assert local.nome == er.nome
				assert local.semDono == (er.sem_dono == 1)
			}

		}
	}

	def getFromJson(String jsonFile) {
		return (new JsonSlurper()).parseText(this.getClass().getResource(jsonFile).getText()).result
	}
}
