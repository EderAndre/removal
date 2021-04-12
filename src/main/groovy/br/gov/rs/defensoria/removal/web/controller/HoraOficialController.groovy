package br.gov.rs.defensoria.removal.web.controller

import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.DataOficial
import br.gov.rs.defensoria.removal.api.HoraOficial

@RestController
class HoraOficialController {
	
	@RequestMapping("hora-oficial")
    HoraOficial getHoraOficial() {
		
		return new HoraOficial(hora: getFormatedCurrentTime('HH:mm'))
	}
	
	@RequestMapping('/edital/{idEdital}/data-oficial')
    DataOficial getDataOficial() {
		
		return new DataOficial(data: getFormatedCurrentTime('dd/MM/yyyy - HH:mm'))
	}

    String getFormatedCurrentTime(String mask) {
		
		LocalDateTime now = LocalDateTime.now()
		DateTimeFormatter fmt = DateTimeFormat.forPattern(mask)
		return fmt.print(now)		
	}
}
