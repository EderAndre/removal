package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.PermissoesEntity
import br.gov.rs.defensoria.removal.repository.entity.ServidoresEntity

interface PermissoesRepository extends JpaRepository<PermissoesEntity, Integer> {
    List<PermissoesEntity> findByServidor(ServidoresEntity servidor)
    
    PermissoesEntity findByServidorMatricula(Integer matricula)
}
