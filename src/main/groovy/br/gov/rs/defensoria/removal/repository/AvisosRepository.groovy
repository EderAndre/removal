package br.gov.rs.defensoria.removal.repository


import java.util.List

import org.joda.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.AvisosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity


interface AvisosRepository extends JpaRepository<AvisosEntity, Integer> {
	AvisosEntity findByIdAviso(int idAviso)
	
	List<AvisosEntity> findByEditalIdEdital(int idEdital)
	
	AvisosEntity findByEditalAndDataInclusaoAndDataAvisoAndTituloAndDescricao(EditaisEntity edital,
			LocalDateTime dataInclusao,
			LocalDateTime dataAviso,
			String tituloAviso,
			String descricaoAviso)
}
