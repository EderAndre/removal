package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.EditalStatus
import br.gov.rs.defensoria.removal.maestro.service.StatusEditalService
import br.gov.rs.defensoria.removal.web.provider.UserApiProvider

@RestController
class EditalMensagemErroController {

	@Autowired
	StatusEditalService statusEditalService

	@Autowired
	UserApiProvider uap

	@RequestMapping('/edital/{idEdital}/bloqueado/info')
    EditalStatus getEditalStatus(@PathVariable int idEdital) {

		int matricula = uap.matricula

		EditalStatus editalStatus = statusEditalService.getEditalStatus(idEdital, matricula)
	}
}
