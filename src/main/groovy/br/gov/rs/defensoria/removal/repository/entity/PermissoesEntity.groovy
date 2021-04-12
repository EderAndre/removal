package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.*

@Entity
@Table(name = "permissoes")
class PermissoesEntity {
	
	@Id
	@TableGenerator(name="permissoesGen", table="sequence_table", pkColumnName="seq_name",
		valueColumnName="seq_count", pkColumnValue="permissoes", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="permissoesGen")
	@Column(name = "id_permissao")
	int idPermissao
	
	@ManyToOne
	@JoinColumn(name = "matricula")
	ServidoresEntity servidor
	
	@ManyToOne
	@JoinColumn(name = "id_edital")
	EditaisEntity edital
			
	@Column(name = "permissao")
	String permissao
	
	
}
