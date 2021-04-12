package br.gov.rs.defensoria.removal.repository

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity

interface FechamentosRepository extends JpaRepository<FechamentosEntity, Integer> {
	FechamentosEntity findByIdFechamento(int idFechamento)
}
