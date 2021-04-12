package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.SnapPretensoesEntity

interface SnapPretensoesRepository extends JpaRepository<SnapPretensoesEntity, Integer> {
	List<SnapPretensoesEntity> findByCodFechamento(int codFechamento)
}
