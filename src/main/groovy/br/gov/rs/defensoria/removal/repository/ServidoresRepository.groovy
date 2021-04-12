package br.gov.rs.defensoria.removal.repository

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.ServidoresEntity

interface ServidoresRepository extends JpaRepository<ServidoresEntity, Integer> {
	ServidoresEntity findByMatricula(int matricula)
}
