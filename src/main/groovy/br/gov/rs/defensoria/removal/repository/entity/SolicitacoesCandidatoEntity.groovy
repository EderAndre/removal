package br.gov.rs.defensoria.removal.repository.entity

import org.joda.time.LocalDateTime
import org.hibernate.annotations.Type

class SolicitacoesCandidatoEntity {
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	LocalDateTime dataSolicitacao

	int numSolicitacoes
	
}
