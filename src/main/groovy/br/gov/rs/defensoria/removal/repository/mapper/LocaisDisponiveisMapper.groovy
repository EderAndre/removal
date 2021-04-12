package br.gov.rs.defensoria.removal.repository.mapper

import java.sql.ResultSet
import java.sql.SQLException

import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.entity.LocaisDisponiveisEntity

@Component
class LocaisDisponiveisMapper implements RowMapper<LocaisDisponiveisEntity> {

    @Override
    LocaisDisponiveisEntity mapRow(ResultSet rs, int rowNum)
    throws SQLException {
        return new LocaisDisponiveisEntity(idVagaEdital: rs.getInt('id_vaga_edital'),
        idUnidade: rs.getInt('unidade'),
        descricao: rs.getString('descricao'),
        edital: rs.getInt('edital'),
        ordemVaga: rs.getInt('ordem_vaga'),
        status: rs.getString('status'))
    }
}
