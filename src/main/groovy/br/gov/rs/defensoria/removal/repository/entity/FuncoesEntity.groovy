package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.OneToMany
import javax.persistence.JoinColumn

@Entity
@Table(name = "funcoes")
class FuncoesEntity {
		
	@Id
	@Column(name = "id_funcao")
	int idFuncao
	
	@Column
	String descricao_funcao
	
	@OneToMany(mappedBy = "funcao")
	List<CandidatosEntity> candidato
}
