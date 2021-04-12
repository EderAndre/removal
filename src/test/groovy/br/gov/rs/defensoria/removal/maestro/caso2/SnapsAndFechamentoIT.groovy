package br.gov.rs.defensoria.removal.maestro.caso2

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.core.RemovalService
import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.domain.Person
import br.gov.rs.defensoria.removal.maestro.CreateSnapshotService
import br.gov.rs.defensoria.removal.maestro.FechamentoDataInputService
import br.gov.rs.defensoria.removal.repository.SnapResultadosRepository
import br.gov.rs.defensoria.removal.repository.entity.SnapResultadosEntity
import groovy.json.JsonSlurper
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
@ActiveProfiles('IT')
@SpringBootTest
class SnapsAndFechamentoIT {

	@Autowired
	CreateSnapshotService createSnapshotService

	@Autowired
	FechamentoDataInputService fechamentoDataInputService

	@Autowired
	RemovalService removalService

	@Autowired
	SnapResultadosRepository snapResultadosRepository

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
		'fechamentosAmbos.sql',
		'snapResultadosTecnicos.sql'
	])
	void testSnapsAndFechamento() {
		int fechamento = 22

		createSnapshotService.createSnapLocaisDisponiveis(fechamento)
		createSnapshotService.createSnapPretensoes(fechamento)

		def inputData = fechamentoDataInputService.getFechamentoData(fechamento)
		List<Office> offices = inputData.offices
		List<Person> candidates = inputData.candidates

		List<Office> allocationResult = removalService.getNewAllocations(offices, candidates)

		createSnapshotService.createSnapResultado(fechamento, allocationResult)

		List<SnapResultadosEntity> snapSRs = snapResultadosRepository.findByCodFechamento(fechamento)

		assert 108 == snapSRs.size()
		assert 43 == snapSRs.findAll {it.matricula != 0}.size()

		def expectedResult = getFromJSON('snap_resultados.json')
		expectedResult.each { er->
			SnapResultadosEntity resultado = snapSRs.find {it.codLocal == er.cod_local}
			if(resultado){
				assert resultado.matricula == er.matricula
				assert resultado.ordemGanha == er.ordem_ganha
				assert resultado.codFechamento == er.cod_fechamento
			}
		}
	}

	def getFromJSON(String jsonFile) {
		return (new JsonSlurper()).parseText(this.getClass().getResource(jsonFile).getText("UTF-8")).result
	}
}
