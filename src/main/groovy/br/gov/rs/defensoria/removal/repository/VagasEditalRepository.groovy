package br.gov.rs.defensoria.removal.repository

import java.util.List

import org.springframework.data.jpa.repository.JpaRepository

import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.UnidadesEntity
import br.gov.rs.defensoria.removal.repository.entity.VagasEditalEntity

interface VagasEditalRepository extends JpaRepository<VagasEditalEntity, Integer>{

    VagasEditalEntity findByIdVagaEdital(int idVagaEdital)

    List<VagasEditalEntity> findByEditalIdEdital(int idEdital)

    VagasEditalEntity findByEditalAndUnidade(EditaisEntity edital, UnidadesEntity unidade)

    List<VagasEditalEntity> findByEdital(EditaisEntity idEdital)
}
