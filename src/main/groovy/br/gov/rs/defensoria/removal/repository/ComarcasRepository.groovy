package br.gov.rs.defensoria.removal.repository

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.ComarcasEntity

interface ComarcasRepository extends JpaRepository<ComarcasEntity, Integer> {

}
