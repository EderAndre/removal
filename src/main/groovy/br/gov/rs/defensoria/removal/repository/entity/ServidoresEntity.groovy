package br.gov.rs.defensoria.removal.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "servidores")
class ServidoresEntity {

	@Id
	int matricula

	@Column(name = "nome_completo")
	String nomeCompleto

	@Column(name = "email_servidor")
	String emailServidor

	@OneToMany(mappedBy = "servidor", fetch = FetchType.EAGER)
	List<CandidatosEntity> candidato

}
