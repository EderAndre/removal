package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "candidatos")
class CandidatosEntity {
		
	@Id
	@Column(name = "id_candidato")
	int idCandidato
	
	@ManyToOne
	@JoinColumn(name = "matricula")
	ServidoresEntity servidor
	
	@ManyToOne
	@JoinColumn(name = "id_edital")
	EditaisEntity edital
	
	@ManyToOne
	@JoinColumn(name = "id_lotacao")
	UnidadesEntity lotacao
		
	@Column
	int antiguidade
	
	@ManyToOne
	@JoinColumn(name = "id_funcao")
	FuncoesEntity funcao
	
	@OneToMany(mappedBy = "candidato", fetch = FetchType.EAGER)
	List<CandidaturasEntity> candidaturas	
}
