package br.gov.rs.defensoria.removal.maestro.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import br.gov.rs.defensoria.removal.api.EditalParaEmail
import br.gov.rs.defensoria.removal.repository.EditaisRepository
import br.gov.rs.defensoria.removal.repository.entity.EditaisEntity
/**
 * Objeto EditalParaEmailService.
 *
 * <P> classe responsável por disponibilizar informações do edital
 * necessárias para envio de email
 *
 * @author Eder André Soares, Unidade de Sistemas-DPE-RS
 *
 */
@Component
class EditalParaEmailService {

	@Autowired
	EditaisRepository editaisRepository

	/**
	 * Retorna um  objeto EditalParaEmail, contendo as propiedades necessárias para os templates 
	 * de envio de email
	 * <p>
	 * Este método realiza a conversão de Objetos EditaisEntity para EditalParaEmail
	 *
	 * @param  idEdital  identificação única do edital persistido no banco de dados
	 * @return um objeto do tipo EditalIntegridade
	 */
	def executaFormatacao(int idEdital){
		EditaisEntity edital = editaisRepository.findByIdEdital(idEdital)
		return formataEditalParaEmail(edital)
	}

    EditalParaEmail formataEditalParaEmail(EditaisEntity edital){
		EditalParaEmail resultado=new EditalParaEmail(
				idEdital:edital.idEdital,
				nomeEdital:edital.descricaoEdital,
				assinaturaEmail: edital.assinaturaEmail,
				emailEnvio: edital.emailEnvio,
				emailResposta: edital.emailResposta
				)
		return resultado
	}
}
