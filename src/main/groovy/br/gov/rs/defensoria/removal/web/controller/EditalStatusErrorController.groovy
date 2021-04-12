package br.gov.rs.defensoria.removal.web.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class EditalStatusErrorController {
	
    @RequestMapping('/edital/{idEdital}/bloqueado')
    String editalBloqueado(@PathVariable int idEdital) {
		
		return '/v2/app/views/partials/erro/edital-bloqueado.html'
    }
}
