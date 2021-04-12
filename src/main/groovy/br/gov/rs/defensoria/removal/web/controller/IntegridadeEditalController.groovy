package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.EditalIntegridade
import br.gov.rs.defensoria.removal.maestro.service.EditalIntegridadeService

@RestController
class IntegridadeEditalController {
	
	@Autowired
	EditalIntegridadeService editalIntegridadeService
	
	@RequestMapping('/edital/{idEdital}/integridade')
    EditalIntegridade getIntegridadeEdital(@PathVariable int idEdital) {
		
		return editalIntegridadeService.executaTesteIntegridade(idEdital)		
	}
}
