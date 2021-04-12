package br.gov.rs.defensoria.removal.repository.mapper
import java.sql.ResultSet
import java.sql.SQLException

import org.joda.time.LocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
import br.gov.rs.defensoria.removal.repository.entity.FechamentosEntity

@Component
class FechamentosMapper implements RowMapper<FechamentosEntity>{

	@Autowired
	EditaisRepository editaisRepository

	@Override
    FechamentosEntity mapRow(ResultSet rs, int rowNum)
	throws SQLException {

		EditaisEntity edital = editaisRepository.findByIdEdital(rs.getInt('id_edital'))

		String[] dateTime = rs.getString('data_fechamento').split(' ')

		String[] yearMonthDay = dateTime[0].split('-')
		Integer year = Integer.parseInt(yearMonthDay[0])
		Integer month = Integer.parseInt(yearMonthDay[1])
		Integer day = Integer.parseInt(yearMonthDay[2])

		String[] hourMinuteSecond = dateTime[1].split(':')
		Integer hour = Integer.parseInt(hourMinuteSecond[0])
		Integer minute = Integer.parseInt(hourMinuteSecond[1])
		Integer second = Float.parseFloat(hourMinuteSecond[2])

		LocalDateTime date = new LocalDateTime(year, month, day, hour, minute, second)

		FechamentosEntity fechamento = new FechamentosEntity(idFechamento: rs.getInt('id_fechamento'),
		edital: edital,
		dataFechamento: date
		)

		return fechamento
	}

}
