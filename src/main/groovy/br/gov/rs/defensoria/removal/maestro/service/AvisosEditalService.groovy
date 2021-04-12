package br.gov.rs.defensoria.removal.maestro.service

import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.api.ObjAvisoEdital
import br.gov.rs.defensoria.removal.maestro.util.TimeUtil
import br.gov.rs.defensoria.removal.repository.AvisosRepository
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.AvisosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity

@Component
class AvisosEditalService {

	@Autowired
	AvisosRepository avisosRepository

	@Autowired
	EditaisRepository editaisRepository

	@Autowired
	TimeUtil timeUtil

    List<ObjAvisoEdital> getAvisosEdital(int idEdital) {

		List<ObjAvisoEdital> infosAvisosEdital = new ArrayList<ObjAvisoEdital>()

		List<AvisosEntity> avisosEdital = avisosRepository.findByEditalIdEdital(idEdital)
		avisosEdital.sort {it.dataAviso}
		avisosEdital = avisosEdital.reverse()
		avisosEdital.each {
			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy - HH:mm")

			infosAvisosEdital.add(new ObjAvisoEdital(idAviso: it.idAviso,
			idEdital: it.edital.idEdital,
			dataAviso: fmt.print(it.dataAviso),
			tituloAviso: it.titulo,
			descricaoAviso: it.descricao,
			excluidoAviso: it.excluido,
			dataInclusao: fmt.print(it.dataInclusao)))
		}

		return infosAvisosEdital
	}

    AvisosEntity setAvisoEdital(ObjAvisoEdital oae) {
		EditaisEntity edital = editaisRepository.findByIdEdital(oae.idEdital)

		AvisosEntity aviso = new AvisosEntity(edital: edital,
		dataInclusao: timeUtil.stringToLocalDateTime(oae.dataInclusao),
		dataAviso: timeUtil.stringToLocalDateTime(oae.dataAviso),
		titulo: oae.tituloAviso,
		descricao: oae.descricaoAviso,
		excluido: 0)

		return avisosRepository.save(aviso)
	}

    AvisosEntity deleteRestauraAvisoEdital(ObjAvisoEdital oae) {
		EditaisEntity edital = editaisRepository.findByIdEdital(oae.idEdital)

		AvisosEntity aviso = new AvisosEntity()
		if (oae.idAviso != 0) {
			aviso = avisosRepository.findOne(oae.idAviso)
		} else {
			aviso = avisosRepository.findByEditalAndDataInclusaoAndDataAvisoAndTituloAndDescricao(edital,
					timeUtil.stringToLocalDateTime(oae.dataInclusao),
					timeUtil.stringToLocalDateTime(oae.dataAviso),
					oae.tituloAviso,
					oae.descricaoAviso)
		}

		if (aviso.excluido) {
			aviso.excluido = 0
		} else {
			aviso.excluido = 1
		}

		return avisosRepository.save(aviso)
	}
}
