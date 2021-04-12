package br.gov.rs.defensoria.removal.web.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.ObjConstroiPedido
import br.gov.rs.defensoria.removal.api.UnidadeConstroiPedido
import br.gov.rs.defensoria.removal.maestro.service.ObjConstroiPedidoService

@RestController
class ObjConstroiPedidoController {
	
	@Autowired
	ObjConstroiPedidoService objConstroiPedidoService	
	
	@RequestMapping("/edital/{edital}/obj-constroi-pedido/candidato/{idCandidato}")
    ObjConstroiPedido getObjConstroiPedido(@PathVariable int edital,
                                           @PathVariable int idCandidato) {
		
		return objConstroiPedidoService.getObjConstroiPedido(edital, idCandidato)
	}
		
	@RequestMapping("/edital/{edital}/obj-constroi-pedido")
    ObjConstroiPedido getObjConstroiPedidoSemCandidato(@PathVariable int edital) {
		
		return objConstroiPedidoService.getObjConstroiPedido(edital, null)
	}
}
