package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "comarcas")
class ComarcasEntity {
		
	@Id
	int id_comarca
		
	@Column
	String descricao_comarca
	
	@OneToMany(mappedBy = "comarca")
	List<UnidadesEntity> unidades
}
