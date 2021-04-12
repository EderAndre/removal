package br.gov.rs.defensoria.removal.repository

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.RestricoesCandidaturasEntity

interface RestricoesCandidaturasRepository extends JpaRepository<RestricoesCandidaturasEntity, Integer> {
	Long countByCandidatoIdCandidatoAndUnidadeIdUnidade(Integer candidatoId, Integer unidadeId)
}
