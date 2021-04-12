package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.api.EditalParaEmail
import br.gov.rs.defensoria.removal.maestro.mail.model.CandidaturaReciboMailModel
import groovy.text.SimpleTemplateEngine
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component

import javax.mail.internet.MimeMessage

import static java.nio.charset.StandardCharsets.UTF_8

@Component
class EmailServiceImpl {
	static String KEY_TESTE_EMAIL_ATIVO = 'teste.email.ativo'
	static String KEY_TESTE_EMAIL_CAIXAS = 'teste.email.caixas'

	@Value('${mail.copycarbon}')
	private String emailCopyCarbon

	@Autowired
	private JavaMailSender mailSender

	@Autowired
	private Environment environment

	@Autowired
	private EditalParaEmailService editalParaEmailService

	private SimpleTemplateEngine templateEngine

	@Value('${mail.address.system}')
	String enderecoSistema

	@Value('${mail.address.support}')
	String enderecoSuporte

	EmailServiceImpl() {
		templateEngine = new SimpleTemplateEngine()
	}

	void enviarReciboCandidatura(CandidaturaReciboMailModel modelo, int editalId) {
		EditalParaEmail edital=editalParaEmailService.executaFormatacao(editalId)

		String textoEmail = compilaTemplateEmail(modelo.template(), modelo, editalId)
		enviaEmail([modelo.email] as String[],  edital.emailResposta, modelo.assunto, textoEmail)
	}

	private String compilaTemplateEmail(String template, Object modelo, int editalId) {
		EditalParaEmail edital=editalParaEmailService.executaFormatacao(editalId)
		def variaveisTemplate = [
			model: modelo,
			endereco_sistema: enderecoSistema,
			contato_suporte: enderecoSuporte,
			assinatura: edital.assinaturaEmail,
			rodape: String.format("E-mail: %s. Sua solicitacao foi realizada com sucesso.",edital.emailEnvio)
		]
		return templateEngine.createTemplate(template).make(variaveisTemplate)
	}

	String[] resolveEmailRecipientes(String[] toOriginal) {
		if(environment.getActiveProfiles().count {it == 'prod'} != 0) {
			return toOriginal
		} else {
			boolean teste_email_ativo = false
			String teste_email_caixas = ''
			try {
				teste_email_ativo = environment.getRequiredProperty(KEY_TESTE_EMAIL_ATIVO) == 'true'
				teste_email_caixas = environment.getRequiredProperty(KEY_TESTE_EMAIL_CAIXAS)
			}catch(java.lang.IllegalStateException ex) {}

			if (teste_email_ativo && teste_email_caixas != '') {
				return teste_email_caixas.tokenize(',') as String[]
			}
		}
		return []
	}

	private void enviaEmail(String[] to, String from, String subject, String emailBody) {
		MimeMessage message = mailSender.createMimeMessage()
		MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8.name())
		helper.setFrom(from)
		helper.setSubject(subject)
		helper.setText(emailBody, true)

		String[] bcc = emailCopyCarbon.split(',')
		if (bcc.any()) {
			helper.setBcc(bcc)
		}

		String[] recipientes = resolveEmailRecipientes(to)
		if (recipientes.any()) {
			helper.setTo(recipientes)
			mailSender.send(message)
		}
	}
}
