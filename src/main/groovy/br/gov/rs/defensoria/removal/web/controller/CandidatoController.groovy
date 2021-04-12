package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.Candidato
import br.gov.rs.defensoria.removal.maestro.service.CandidatoService

@RestController
@RequestMapping("/edital/{editalId}/candidato")
class CandidatoController {
	
	@Autowired
	private CandidatoService candidatoService
	
	@RequestMapping(value = "/busca", method = RequestMethod.GET)
    void busca(@PathVariable Integer editalId) {
	}

	@RequestMapping(value = "/{candidatoId}/info", method = RequestMethod.GET)
    void info(@PathVariable Integer editalId, @PathVariable Integer candidatoId) {
	}
	@RequestMapping(value = "/lista", method = RequestMethod.GET)
    List <Candidato> lista(@PathVariable Integer editalId) {
		return candidatoService.exibeCandidatosEdital(editalId)
	}
}