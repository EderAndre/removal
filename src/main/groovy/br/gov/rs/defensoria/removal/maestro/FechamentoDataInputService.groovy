package br.gov.rs.defensoria.removal.maestro

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.domain.Office
import br.gov.rs.defensoria.removal.domain.Person
import br.gov.rs.defensoria.removal.repository.CandidatosRepository
import br.gov.rs.defensoria.removal.repository.DadosFechamentosRepository
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.FechamentosRepository
import br.gov.rs.defensoria.removal.repository.FuncoesRepository
import br.gov.rs.defensoria.removal.repository.ServidoresRepository
import br.gov.rs.defensoria.removal.repository.SnapLocaisRepository
import br.gov.rs.defensoria.removal.repository.TodosOsLocaisRepository
import br.gov.rs.defensoria.removal.repository.UnidadesRepository
import br.gov.rs.defensoria.removal.repository.VagasEditalRepository
import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.CandidaturasEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity
import br.gov.rs.defensoria.removal.repository.entity.FuncoesEntity
import br.gov.rs.defensoria.removal.repository.entity.PreCandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.PreCandidaturasEntity
import br.gov.rs.defensoria.removal.repository.entity.ServidoresEntity
import br.gov.rs.defensoria.removal.repository.entity.SnapLocaisEntity
import br.gov.rs.defensoria.removal.repository.entity.TodosOsLocaisEntity
import br.gov.rs.defensoria.removal.repository.entity.UnidadesEntity
import br.gov.rs.defensoria.removal.repository.entity.VagasEditalEntity

@Component
class FechamentoDataInputService {

	@Autowired
	DadosFechamentosRepository dadosFechamentosRepository

	@Autowired
	SnapLocaisRepository snapLocaisRepository

	@Autowired
	TodosOsLocaisRepository todosOsLocaisRepository

	@Autowired
	VagasEditalRepository vagasEditalRepository

	@Autowired
	ServidoresRepository servidoresRepository

	@Autowired
	EditaisRepository editaisRepository

	@Autowired
	UnidadesRepository unidadesRepository

	@Autowired
	FuncoesRepository funcoesRepository

	@Autowired
	FechamentosRepository fechamentosRepository
	
	@Autowired
	CandidatosRepository candidatosRepository

	private final Logger LOGGER = LoggerFactory.getLogger(this.class)

	def getFechamentoData(int fechamento) {

		int idEdital = fechamentosRepository.findByIdFechamento(fechamento).edital.idEdital
		List<TodosOsLocaisEntity> todosOsLocaisEntitys = todosOsLocaisRepository.getTodosOsLocais(idEdital)
		List<Office> todosOsLocais = todosOsLocaisEntitysToOffices(todosOsLocaisEntitys)

		List<SnapLocaisEntity> locaisDisponiveis = snapLocaisRepository.findByIdFechamento(fechamento)

		List<PreCandidaturasEntity> preCandidaturas = dadosFechamentosRepository.getCandidaturas(fechamento)

		List<PreCandidatosEntity> preCandidatos = dadosFechamentosRepository.getCandidatos(fechamento)

		List<VagasEditalEntity> vagasEdital = vagasEditalRepository.findByEditalIdEdital(idEdital)

		List<ServidoresEntity> servidores = servidoresRepository.findAll()

		List<EditaisEntity> editais = editaisRepository.findAll()

		List<UnidadesEntity> lotacoes = unidadesRepository.findAll()

		List<FuncoesEntity> funcoes = funcoesRepository.findAll()

		List<CandidatosEntity> candidatos = new ArrayList<CandidatosEntity>()
		preCandidatos.each { preCandidato->
			candidatos.add(new CandidatosEntity(idCandidato: preCandidato.idCandidato,
			servidor: servidores.find {it.matricula == preCandidato.matricula},
			edital: editais.find {it.idEdital == preCandidato.idEdital},
			lotacao: lotacoes.find {it.idUnidade == preCandidato.idLotacao},
			antiguidade: preCandidato.antiguidade,
			funcao: funcoes.find {it.idFuncao == preCandidato.idFuncao}))
		}

		List<CandidaturasEntity> candidaturas = new ArrayList<CandidaturasEntity>()
		preCandidaturas.each { preCandidatura->
			candidaturas.add(new CandidaturasEntity(idCandidatura: preCandidatura.idPretensao,
			vagaEdital: vagasEdital.find {it.idVagaEdital == preCandidatura.idLocal},
			candidato: candidatos.find {it.idCandidato == preCandidatura.idCandidato},
			ordemCandidatura: preCandidatura.ordem))
		}

		candidatos.each { candidato->
			candidato.candidaturas = candidaturas.findAll {it.candidato == candidato}
		}

		def domainObjects = convertEntityToDomain(candidatos, locaisDisponiveis, todosOsLocais, vagasEdital)

		return [candidates: domainObjects.candidates, offices: domainObjects.offices]
	}

	List<Office> todosOsLocaisEntitysToOffices(List<TodosOsLocaisEntity> todosOsLocaisEntitys) {
		List<Office> offices = new ArrayList<Office>()

		todosOsLocaisEntitys.each {
			offices.add(new Office(id: it.idLocal,
			name: it.descricao,
			hasOwner: it.ocupante != 0))
		}

		return offices
	}

	def convertEntityToDomain(List<CandidatosEntity> candidatosEntity, List<SnapLocaisEntity> locaisEntity, List<Office> todosOsLocais, List<VagasEditalEntity> vagasEdital) {
		List<Person> candidatos	= new ArrayList<Person>()
		List<Office> offices = new ArrayList<Office>()

		locaisEntity.each { local->
			offices.add(todosOsLocais.find {it.id == local.idLocal})
		}

		candidatosEntity.each {
			candidatos.add(candidatoToPerson(it, todosOsLocais, vagasEdital))
		}

		return [candidates: candidatos, offices: offices]
	}

	Person candidatoToPerson(CandidatosEntity entity, List<Office> todosOsLocais, List<VagasEditalEntity> vagasEdital) {
		VagasEditalEntity vaga = vagasEdital.find {it.unidade.idUnidade == entity.lotacao?.idUnidade}
		Office allocation = null
		if (vaga != null) {
			allocation = todosOsLocais.find {it.id == vaga.idVagaEdital}
		}

		Person person = new Person(id: entity.servidor.matricula,
		antiquity: entity.antiguidade,
		name: entity.servidor.nomeCompleto,
		allocation: allocation)

		allocation?.setOccupant(person)

		List<Office> pretensions = new ArrayList<Office>()
		entity.candidaturas.each {
			if (it.vagaEdital == null) {
				allocation?.blocked = true
				LOGGER.info("Desistencia encontrada no candidato ${it.candidato.servidor.matricula}.")
			} else {
				pretensions.add(todosOsLocais.find { tl->
					it.vagaEdital.idVagaEdital == tl.id
				})
			}
		}
		person.setPretensions(pretensions)

		return person
	}
	
	def getFechamentoDataWithAllocations(int fechamento) {		
		def fechamentoData = getFechamentoData(fechamento)
		def candidatos = fechamentoData['candidates']
		
		FechamentosEntity fechamentoEntity = fechamentosRepository.findOne(fechamento)
		List<CandidatosEntity> candidatosEntity = candidatosRepository.findByEdital(fechamentoEntity.edital)
		List<UnidadesEntity> unidades = unidadesRepository.findAll()
		
		candidatos.each { c ->
			CandidatosEntity candidatoEntity = candidatosEntity.find {it.servidor.matricula == c.id}
			UnidadesEntity unidade = unidades.find {it.idUnidade == candidatoEntity.lotacao?.idUnidade}
						
			int idUnidade = unidade != null ? unidade.idUnidade : 0
			Office allocation = new Office(id: idUnidade,
				name: unidade?.descricao)
			c.allocation = allocation
		}
		
		return [candidates: candidatos, offices: fechamentoData['offices']]
	}
}
