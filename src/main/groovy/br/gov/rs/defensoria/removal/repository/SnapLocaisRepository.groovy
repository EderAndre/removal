package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.SnapLocaisEntity

interface SnapLocaisRepository extends JpaRepository<SnapLocaisEntity, Integer>{
	List<SnapLocaisEntity> findByIdFechamento(int idFechamento)
}
