package br.gov.rs.defensoria.removal.maestro

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.core.RemovalService
import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.domain.Person
import br.gov.rs.defensoria.removal.repository.DadosFechamentosRepository
import br.gov.rs.defensoria.removal.repository.SnapLocaisRepository
import br.gov.rs.defensoria.removal.repository.SnapPretensoesRepository
import br.gov.rs.defensoria.removal.repository.SnapResultadosRepository
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity
import br.gov.rs.defensoria.removal.repository.entity.SnapResultadosEntity

@Component
class FechamentoService {

	@Autowired
	FechamentoDataInputService fechamentoDataInputService

	@Autowired
	RemovalService removalService

	@Autowired
	SnapResultadosRepository snapResultadosRepository

	@Autowired
	DadosFechamentosRepository dadosFechamentosRepository

	@Autowired
	SnapLocaisRepository snapLocaisRepository

	@Autowired
	SnapPretensoesRepository snapPretensoesRepository

	@Autowired
	CreateSnapshotService createSnapshotService

	private final Logger LOGGER = LoggerFactory.getLogger(FechamentoService.class)

    String computeFechamento(boolean byEdital = false, int fechamentoOrIdEdital, boolean shouldPersistIn=false, boolean shouldPersistOut=false) {
		int passo = 1
		LOGGER.info("${passo++} - Detectar fechamento por edital: ${byEdital}.")
		LOGGER.info("${passo++} - Persistir a entrada: ${shouldPersistIn}. Persistir a saida: ${shouldPersistOut}.")

		int fechamento = fechamentoOrIdEdital
		String exprDateFechamento = ""

		if (byEdital) {
			FechamentosEntity fechamentoEntity = dadosFechamentosRepository.getGreater(fechamentoOrIdEdital)
			fechamento = fechamentoEntity.idFechamento
			exprDateFechamento = " com data ${fechamentoEntity.dataFechamento}"
		}
		LOGGER.info("${passo++} - Fechamento ${fechamento}${exprDateFechamento}.")

		if(shouldPersistIn) {
			int qtdSnapLocais = snapLocaisRepository.findByIdFechamento(fechamento).size()
			int qtdSnapPretensoes = snapPretensoesRepository.findByCodFechamento(fechamento).size()

			if (qtdSnapLocais + qtdSnapPretensoes > 0) {
				LOGGER.info("Snapshot de entrada existente.")
			} else {
				createSnapshotService.createSnapLocaisDisponiveis(fechamento)
				LOGGER.info("Snapshot de locais realizado.")

				createSnapshotService.createSnapPretensoes(fechamento)
				LOGGER.info("Snapshot de pretensoes realizado.")
			}
		}

		//converte entidade em dominio
		def inputData = fechamentoDataInputService.getFechamentoData(fechamento)

		List<Office> offices = inputData.offices
		LOGGER.info("${passo++} - Obtencao de locais processada. Quantidade: ${offices.size()}.")

		List<Person> candidates = inputData.candidates
		LOGGER.info("${passo++} - Obtencao de candidatos processada. Quantidade: ${candidates.size()}.")

		//executa fechamento
		List<Office> allocationResult = removalService.getNewAllocations(offices, candidates)
		LOGGER.info("${passo++} - Alocacoes finalizadas. Quantidade: ${allocationResult.size()}.")

		//persiste fechamento
		if (shouldPersistOut) {
			List<SnapResultadosEntity> snapsResultadoFechamento = snapResultadosRepository.findByCodFechamento(fechamento)
			if (snapsResultadoFechamento.size() == 0) {
				LOGGER.info("${passo++} - Nao existe snapshot para saida. Criando.")
				createSnapshotService.createSnapResultado(fechamento, allocationResult)
				LOGGER.info("${passo++} - Snapshot do resultado realizado.")
			} else {
				LOGGER.info("${passo++} - Ja existe snapshot para saida.")
			}
		}


		//gera documentos (pdf, csv, etc)
		"Fim do metodo 'computeFechamento()'."
	}

}
