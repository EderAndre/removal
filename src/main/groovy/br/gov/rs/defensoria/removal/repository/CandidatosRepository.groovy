package br.gov.rs.defensoria.removal.repository

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.ServidoresEntity


interface CandidatosRepository extends JpaRepository<CandidatosEntity, Integer> {

	CandidatosEntity findByIdCandidato(int idCandidato)

	CandidatosEntity findByLotacaoIdUnidade(int idLotacao)

	CandidatosEntity findByServidorAndEdital(ServidoresEntity servidor, EditaisEntity edital)

	List<CandidatosEntity> findByServidor(ServidoresEntity servidor)
	
	List<CandidatosEntity> findByEdital(EditaisEntity edital)
}
