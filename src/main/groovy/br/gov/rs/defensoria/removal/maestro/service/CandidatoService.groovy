package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.api.Candidato
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CandidatoService {

	@Autowired
	EditaisRepository editaisRepository

	private String msgErro="Não existem candidatos listados para este edital"

	List <Candidato> exibeCandidatosEdital(int idEdital)  {
		List <Candidato> candidatos=[]
		getCandidatos(idEdital).each{
			candidatos.push(
					idCandidato: it.idCandidato,
					antiguidade:it.antiguidade,
					nomeCompleto:it.servidor.nomeCompleto,
					descricaoLotacao:it.lotacao?.descricao,
					email:it.servidor.emailServidor
					)
		}
		if(candidatos.size>0){
			return candidatos
		}
		else{
			return msgErro
		}
	}

	private  List <CandidatosEntity> getCandidatos(int idEdital){
		EditaisEntity edital=editaisRepository.findByIdEdital(idEdital)
		return edital.candidato
	}
}
