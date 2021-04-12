package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.SnapResultadosEntity

interface SnapResultadosRepository extends JpaRepository<SnapResultadosEntity, Integer> {
	List<SnapResultadosEntity> findByCodFechamento(int codFechamento)
}
