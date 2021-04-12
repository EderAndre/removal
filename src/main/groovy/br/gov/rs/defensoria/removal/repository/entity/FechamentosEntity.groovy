package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

import org.hibernate.annotations.Type
import org.joda.time.LocalDateTime

@Entity
@Table(name = "fechamentos")
class FechamentosEntity {
	
	@Id
	@Column(name = "id_fechamento")
	int idFechamento
	
	@ManyToOne
	@JoinColumn(name = "id_edital")
	EditaisEntity edital
	
	@Column(name = 'data_fechamento')
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	LocalDateTime dataFechamento
	
	@Column(name = 'relatorio_fechamento')
	String relatorioFechamento
	
	@Column(name = 'relatorio_preliminar')
	String relatorioPreliminar
}
