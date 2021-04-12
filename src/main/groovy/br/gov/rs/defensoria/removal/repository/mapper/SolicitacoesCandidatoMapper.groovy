package br.gov.rs.defensoria.removal.repository.mapper

import br.gov.rs.defensoria.removal.repository.entity.SolicitacoesCandidatoEntity
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

import java.sql.ResultSet
import java.sql.SQLException

import static org.joda.time.DateTime.parse
import static org.joda.time.format.DateTimeFormat.forPattern

@Component
class SolicitacoesCandidatoMapper implements RowMapper<SolicitacoesCandidatoEntity> {

    @Override
    SolicitacoesCandidatoEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        DateTime dataBD = getDate(rs.getString('data'))
        LocalDateTime date = new LocalDateTime(dataBD)

        return new SolicitacoesCandidatoEntity(dataSolicitacao: date,
                numSolicitacoes: rs.getInt('num_sol'))
    }

    private DateTime getDate(String raw) {
        try {
            return parse(raw, forPattern('yyyy-MM-dd HH:mm:ss.SSSSSSSSS'))
        } catch(IllegalArgumentException ignored) {
            try {
                return parse(raw, forPattern('yyyy-MM-dd HH:mm:ss.SSSSSSS'))
            } catch(IllegalArgumentException argException) {
                return parse(raw, forPattern('yyyy-MM-dd HH:mm:ss'))
            }
        }
    }
}
