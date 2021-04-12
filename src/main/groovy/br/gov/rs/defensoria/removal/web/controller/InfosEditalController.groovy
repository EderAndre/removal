package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.ObjInfosEdital
import br.gov.rs.defensoria.removal.maestro.service.InfosEditalService

@RestController
class InfosEditalController {

	@Autowired
	InfosEditalService infosEditalService

	@RequestMapping("/edital/{idEdital}/infos-edital")
    ObjInfosEdital getInfosEdital(@PathVariable int idEdital) {

		return infosEditalService.getInfosEdital(idEdital)
	}

	@RequestMapping("/edital/{idEdital}/salva-edital")
    void setEdital(@RequestBody ObjInfosEdital oie) {

		infosEditalService.setEdital(oie)
	}
}
