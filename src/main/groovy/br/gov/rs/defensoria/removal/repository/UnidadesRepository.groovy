package br.gov.rs.defensoria.removal.repository

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.UnidadesEntity

interface UnidadesRepository extends JpaRepository<UnidadesEntity, Integer>{
	UnidadesEntity findByIdUnidade(int idUnidade)
}
