package br.gov.rs.defensoria.removal.web.controller

import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import br.gov.rs.defensoria.removal.api.Fechamento
import br.gov.rs.defensoria.removal.maestro.service.ListaFechamentosService
import br.gov.rs.defensoria.removal.maestro.service.RelatorioService

@RestController
@RequestMapping("/edital/{editalId}/fechamentos")
class FechamentosController {
	
	@Autowired
	private ListaFechamentosService fechamentoService
	
	@Autowired
	private RelatorioService relatorioService
	
	@RequestMapping(value = "/lista", method = RequestMethod.GET)
    List <Fechamento> lista(@PathVariable Integer editalId) {
		return fechamentoService.exibeFechamentosEdital(editalId)
	}
	
	@RequestMapping(value = "/{idFechamento}/relatorio/{tipoRelatorio}/download", method = RequestMethod.GET)
    visualizarArquivoController(
			@PathVariable int idFechamento
			, @PathVariable String tipoRelatorio
			, HttpServletResponse response
	)throws Exception{
		def executor=relatorioService.exibeRelatorio(idFechamento, response,tipoRelatorio)
		if(executor!=true)
		throw new Exception(executor)
	}
	
}