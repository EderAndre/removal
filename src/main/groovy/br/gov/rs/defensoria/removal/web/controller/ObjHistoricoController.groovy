package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.ObjHistorico
import br.gov.rs.defensoria.removal.maestro.service.ObjHistoricoService

@RestController
class ObjHistoricoController {
	
	@Autowired
	ObjHistoricoService objHistoricoService
	
	@RequestMapping("/edital/{idEdital}/obj-historico/candidato/{idCandidato}")
    ObjHistorico getHistorico(@PathVariable int idEdital, @PathVariable int idCandidato) {
		
		return objHistoricoService.getHistorico(idEdital, idCandidato)
	}
}
