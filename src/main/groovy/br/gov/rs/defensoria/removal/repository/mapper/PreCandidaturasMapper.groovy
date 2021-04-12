package br.gov.rs.defensoria.removal.repository.mapper

import java.sql.ResultSet
import java.sql.SQLException

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.PreCandidaturasEntity

@Component
class PreCandidaturasMapper implements RowMapper<PreCandidaturasEntity>{
	
	@Override
    PreCandidaturasEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {		
		return new PreCandidaturasEntity(idPretensao: rs.getInt('id_pretensao'),
			idCandidato: rs.getInt('id_candidato'),
			idLocal: rs.getInt('cod_local'),
			ordem: rs.getInt('ordem'))
	}

}
