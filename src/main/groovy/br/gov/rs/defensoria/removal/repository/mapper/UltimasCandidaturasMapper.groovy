package br.gov.rs.defensoria.removal.repository.mapper

import java.sql.ResultSet
import java.sql.SQLException

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.UltimasCandidaturasEntity

@Component
class UltimasCandidaturasMapper implements RowMapper<UltimasCandidaturasEntity> {

	@Override
    UltimasCandidaturasEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		return new UltimasCandidaturasEntity(idCandidatura: rs.getInt('id_candidatura'),
			idVagaEdital: rs.getInt('id_vaga_edital'),
			matricula: rs.getInt('matricula'),
			ordemCandidatura: rs.getInt('ordem_candidatura'))
	}

}
