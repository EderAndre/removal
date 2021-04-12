package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.ObjTelaInicial
import br.gov.rs.defensoria.removal.maestro.service.ObjTelaInicialService
import br.gov.rs.defensoria.removal.web.provider.UserApiProvider

@RestController
class ObjTelaInicialController {

	@Autowired
	ObjTelaInicialService objTelaInicialService

	@Autowired
	UserApiProvider uap

	@RequestMapping("/obj-tela-inicial")
    ObjTelaInicial getObjTelaInicial() {

		String perfil = uap.getRemovalRole()
		int matricula = uap.getMatricula()
		String nome = uap.getNome()

		return objTelaInicialService.getObjTelaInicial(matricula, perfil, nome)
	}

	@RequestMapping("/obj-tela-inicial/matricula/{matricula}/perfil/{perfil}")
    ObjTelaInicial getObjTelaInicialSimulacao(@PathVariable int matricula, @PathVariable String perfil) {

		return objTelaInicialService.getObjTelaInicial(matricula, perfil, null)
	}
}
