package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "unidades")
class UnidadesEntity {
	
	@Id
	@Column(name = "id_unidade")
	int idUnidade
	
	@Column
	String codigo
	
	@Column
	String descricao
	
	@ManyToOne
	@JoinColumn(name = "id_comarca")
	ComarcasEntity comarca
}
