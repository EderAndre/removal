package br.gov.rs.defensoria.removal.repository

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.FuncoesEntity

interface FuncoesRepository extends JpaRepository<FuncoesEntity, Integer> {
	FuncoesEntity findByIdFuncao(int idFuncao)
}
