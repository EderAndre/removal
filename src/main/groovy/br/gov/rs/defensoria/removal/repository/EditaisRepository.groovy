package br.gov.rs.defensoria.removal.repository

import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import org.joda.time.LocalDateTime
import org.springframework.data.jpa.repository.JpaRepository

interface EditaisRepository extends JpaRepository<EditaisEntity, Integer> {
	EditaisEntity findByIdEdital(int idEdital)

    List<EditaisEntity> findByEncerramentoEditalGreaterThanEqual(LocalDateTime reference)
}
