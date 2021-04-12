package br.gov.rs.defensoria.removal.repository.mapper

import java.sql.ResultSet
import java.sql.SQLException

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.TodosOsLocaisEntity

@Component
class TodosOsLocaisMapper implements RowMapper<TodosOsLocaisEntity> {

	@Override
    TodosOsLocaisEntity mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		return new TodosOsLocaisEntity(idLocal: rs.getInt('id_local'),
			descricao: rs.getString('descricao'),
			ocupante: rs.getInt('ocupante'))
	}
	
}
