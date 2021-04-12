package br.gov.rs.defensoria.removal.maestro.caso2

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.maestro.CreateSnapshotService
import br.gov.rs.defensoria.removal.repository.SnapPretensoesRepository
import br.gov.rs.defensoria.removal.repository.entity.SnapPretensoesEntity
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
class SnapInPretensoesIT {

	@Autowired
	CreateSnapshotService createSnapshotService

	@Autowired
	SnapPretensoesRepository snapPretensoesRepository

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
	void snapInPretensoes() {
		createSnapshotService.createSnapPretensoes(22)

		List<SnapPretensoesEntity> savedSPs = snapPretensoesRepository.findAll()
		assertEquals(527, savedSPs.size())

		def expectedResult = getFromJson('snap_pretensoes.json')

		expectedResult.each { er->
			SnapPretensoesEntity pretensao = savedSPs.find {it.matricula == er.matricula && it.ordem == er.ordem}
			assert pretensao.codFechamento == er.cod_fechamento
			assert pretensao.codLocal == er.cod_local
		}
	}

	def getFromJson(String jsonFile) {
		return (new JsonSlurper()).parseText(this.getClass().getResource(jsonFile).getText("UTF-8")).result
	}
}
