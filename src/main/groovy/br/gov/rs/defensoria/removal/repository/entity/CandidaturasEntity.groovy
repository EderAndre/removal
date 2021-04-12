package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.TableGenerator

import org.hibernate.annotations.Type
import org.joda.time.LocalDateTime

@Entity
@Table(name = "candidaturas")
class CandidaturasEntity {
	@Id
	@TableGenerator(name="table_candidaturas_id", table="sequence_table", pkColumnName="seq_name",
		valueColumnName="seq_count", pkColumnValue="candidaturas", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_candidaturas_id")
	@Column(name = "id_candidatura")
    int idCandidatura
	
	@ManyToOne
	@JoinColumn(name = "id_vaga_edital", nullable = true)
	VagasEditalEntity vagaEdital
	
	@ManyToOne
	@JoinColumn(name="id_candidato")
	CandidatosEntity candidato
	
	@Column
	int ordemCandidatura
	
	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	LocalDateTime data
	
}
