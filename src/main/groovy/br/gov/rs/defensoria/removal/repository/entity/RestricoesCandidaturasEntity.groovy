package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn

import org.hibernate.annotations.Type
import org.joda.time.LocalDateTime

@Entity
@Table(name = "restricoes_candidaturas")
class RestricoesCandidaturasEntity {
		
	@Id
	int id_restricao
	
	@ManyToOne
	@JoinColumn(name="id_candidato")
	CandidatosEntity candidato
	
	@ManyToOne
	@JoinColumn(name="id_unidade")
	UnidadesEntity unidade
	
}
