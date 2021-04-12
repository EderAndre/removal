package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.TableGenerator

import org.joda.time.LocalDateTime
import org.hibernate.annotations.Type


@Entity
@Table(name = "avisos")
class AvisosEntity {
	@Id
	@TableGenerator(name="table_avisos_id", table="sequence_table", pkColumnName="seq_name",
		valueColumnName="seq_count", pkColumnValue="avisos", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_avisos_id")
	@Column(name = "id_aviso")
	int idAviso
			
	@ManyToOne
	@JoinColumn(name = "id_edital")
	EditaisEntity edital
	
	@Column(name = "data_inclusao")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	LocalDateTime dataInclusao
	
	@Column(name = "data_aviso")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	LocalDateTime dataAviso
		
	@Column(name = "titulo_aviso")
	String titulo
	
	@Column(name = "descricao_aviso")
	String descricao
	
	@Column(name = "excluido_aviso")
	int excluido
	
}
