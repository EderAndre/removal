package br.gov.rs.defensoria.removal.maestro

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.repository.FechamentosRepository
import br.gov.rs.defensoria.removal.repository.LocaisDisponiveisRepository
import br.gov.rs.defensoria.removal.repository.SnapLocaisRepository
import br.gov.rs.defensoria.removal.repository.SnapPretensoesRepository
import br.gov.rs.defensoria.removal.repository.SnapResultadosRepository
import br.gov.rs.defensoria.removal.repository.UltimasCandidaturasRepository
import br.gov.rs.defensoria.removal.repository.entity.LocaisDisponiveisEntity
import br.gov.rs.defensoria.removal.repository.entity.SnapLocaisEntity
import br.gov.rs.defensoria.removal.repository.entity.SnapPretensoesEntity
import br.gov.rs.defensoria.removal.repository.entity.SnapResultadosEntity
import br.gov.rs.defensoria.removal.repository.entity.UltimasCandidaturasEntity

@Component
class CreateSnapshotService {

	@Autowired
	LocaisDisponiveisRepository locaisDisponiveisRepository

	@Autowired
	SnapLocaisRepository snapLocaisRepository

	@Autowired
	UltimasCandidaturasRepository ultimasCandidaturasRepository

	@Autowired
	SnapPretensoesRepository snapPretensoesRepository

	@Autowired
	FechamentosRepository fechamentosRepository

	@Autowired
	SnapResultadosRepository snapResultadosRepository

	void createSnapLocaisDisponiveis(int fechamento) {
		int idEdital = fechamentosRepository.findByIdFechamento(fechamento).edital.idEdital
		List<LocaisDisponiveisEntity> locaisDisponiveis = locaisDisponiveisRepository.getLocaisDisponiveis(idEdital)
		List<SnapLocaisEntity> snapLocais = new ArrayList<SnapLocaisEntity>()

		locaisDisponiveis.each {
			snapLocais.add(new SnapLocaisEntity(idFechamento: fechamento,
			idLocal: it.idVagaEdital,
			nome: it.descricao,
			semDono: it.edital == 1))
		}
		snapLocaisRepository.saveAll(snapLocais)
	}

	void createSnapPretensoes(int fechamento) {
		int idEdital = fechamentosRepository.findByIdFechamento(fechamento).edital.idEdital
		List<UltimasCandidaturasEntity> ultimasCandidaturas = ultimasCandidaturasRepository.getUltimasCandidaturasByFechamento(fechamento, idEdital)
		List<SnapPretensoesEntity> snapPretensoes = new ArrayList<SnapPretensoesEntity>()

		ultimasCandidaturas.each {
			snapPretensoes.add(new SnapPretensoesEntity(matricula: it.matricula,
			codFechamento: fechamento,
			codLocal: it.idVagaEdital,
			ordem: it.ordemCandidatura))
		}
		snapPretensoesRepository.saveAll(snapPretensoes)
	}

	void createSnapResultado(int fechamento, List<Office> allocationResult) {
		List<SnapResultadosEntity> snapsResultado = new ArrayList<SnapResultadosEntity>()
		allocationResult.each { a->
			snapsResultado.add(new SnapResultadosEntity(codFechamento: fechamento,
			codLocal: a.id,
			matricula: a.pretender == null ? 0 : a.pretender.id,
			ordemGanha: a.pretender == null ? 0 : a.pretender?.pretensions.findIndexOf {it.id == a.id} + 1)
			)
		}
		snapResultadosRepository.saveAll(snapsResultado)
	}

}
