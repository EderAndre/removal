package br.gov.rs.defensoria.removal.repository.mapper

import java.sql.ResultSet
import java.sql.SQLException

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.PreCandidatosEntity

@Component
class PreCandidatosMapper implements RowMapper<PreCandidatosEntity> {
	
	@Override
    PreCandidatosEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new PreCandidatosEntity(idCandidato: rs.getInt('id_candidato'),
			matricula: rs.getInt('matricula'),
			idEdital: rs.getInt('id_edital'),
			idLotacao: rs.getInt('id_lotacao'),
			idFuncao: rs.getInt('id_funcao'),
			antiguidade: rs.getInt('antiguidade'))
	}

}
