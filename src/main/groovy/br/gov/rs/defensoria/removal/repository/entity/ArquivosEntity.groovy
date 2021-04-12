package br.gov.rs.defensoria.removal.repository.entity

import org.hibernate.annotations.Type
import org.joda.time.LocalDateTime

import javax.persistence.*

@Entity
@Table(name = "arquivos")
class ArquivosEntity {
	
	@Id
	@TableGenerator(name="arquivosGen", table="sequence_table", pkColumnName="seq_name",
		valueColumnName="seq_count", pkColumnValue="arquivos", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="arquivosGen")
	@Column(name = "id_arquivo")
	int idArquivo
			
	@ManyToOne
	@JoinColumn(name = "id_edital")
	EditaisEntity edital
	
	@Column(name = "data_arquivo")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	LocalDateTime dataArquivo
	
	@Column(name = "descricao_arquivo")
	String descricao
	
	@Column(name = "nome_arquivo")
	String nomeArquivo
	
	@Column(name = "exibir_arquivo")
	int exibir
	
}
