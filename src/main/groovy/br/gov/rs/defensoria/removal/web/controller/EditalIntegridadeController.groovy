package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.EditalIntegridade
import br.gov.rs.defensoria.removal.maestro.service.EditalIntegridadeService


@RestController
class EditalIntegridadeController {
	
	@Autowired
	EditalIntegridadeService editalIntegridadeService

	
	@RequestMapping('/edital/{idEdital}/teste/integridade')
    EditalIntegridade verificaIntegridade(@PathVariable int idEdital) {
		
		EditalIntegridade resultado= editalIntegridadeService.executaTesteIntegridade(idEdital)
		return resultado
	}
}
