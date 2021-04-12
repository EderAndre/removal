package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.ObjAvisoEdital
import br.gov.rs.defensoria.removal.maestro.service.AvisosEditalService

@RestController
class AvisosEditalController {
	
	@Autowired
	AvisosEditalService avisosEditalService
	
	@RequestMapping('/edital/{idEdital}/avisos-edital')
    List<ObjAvisoEdital> getObjAvisoEdital(@PathVariable int idEdital) {
		
		return avisosEditalService.getAvisosEdital(idEdital)
	}
	
	@RequestMapping('/edital/{idEdital}/salva-aviso')
    void setObjAvisoEdital(@RequestBody ObjAvisoEdital oae) {
		
		avisosEditalService.setAvisoEdital(oae)
	}
	
	@RequestMapping('/edital/{idEdital}/remove-restaura-aviso')
    void delObjAvisoEdital(@RequestBody ObjAvisoEdital oae) {
		
		avisosEditalService.deleteRestauraAvisoEdital(oae)
	}
}
