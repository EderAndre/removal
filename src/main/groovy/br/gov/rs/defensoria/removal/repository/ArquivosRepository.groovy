package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.ArquivosEntity
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity

interface ArquivosRepository extends JpaRepository<ArquivosEntity, Integer> {
	ArquivosEntity findByIdArquivo(int idArquivo)
	
	List<ArquivosEntity> findByEdital(EditaisEntity edital)
	
	List<ArquivosEntity> findByEditalIdEdital(int idEdital)
}
