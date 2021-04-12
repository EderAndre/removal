package br.gov.rs.defensoria.removal.repository

import org.joda.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.CandidatosEntity
import br.gov.rs.defensoria.removal.repository.entity.CandidaturasEntity

interface CandidaturasRepository extends JpaRepository<CandidaturasEntity, Integer> {

	List<CandidaturasEntity> findByCandidato(CandidatosEntity candidato)

	List<CandidaturasEntity> findByCandidatoAndDataLessThanEqual(CandidatosEntity candidato, LocalDateTime dataLimite)
}
