package br.gov.rs.defensoria.removal.maestro.service

import org.springframework.stereotype.Component

@Component
class EncaminhaErrorService {
	
	String encaminha(String msg) {
		
		String caminho = '/v2/app/views/partials/error/'		
		def mapa = ['Edital inexistente para o código': "cod-edital-inexistente.html",
			'Edital bloqueado pelo administrador': "edital-bloqueado-administrador.html",
			'Edital já encerrado': "edital-encerrado.html",
			'O edital ainda não está aberto': "edital-nao-aberto-ainda.html"]
		
		String pagina
		mapa.find { key, value ->
			if (msg.contains(key)) {
				pagina = caminho + value
				return true
			}
			return false
		}
		
		return pagina
	}
}
