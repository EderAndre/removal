package br.gov.rs.defensoria.removal.maestro.service

import br.gov.rs.defensoria.removal.ITConfiguration
import br.gov.rs.defensoria.removal.maestro.mail.model.CandidaturaReciboMailModel
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.mail.MailSender
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@ActiveProfiles("IT")
@SpringBootTest
class EmailServiceTest {

	@Autowired
	private EmailServiceImpl emailService

	@Autowired
	private MailSender mailSender

	void exemploEnvioReciboCandidatura() {
		def model = new CandidaturaReciboMailModel(
				email: 'douglas-correa@dpe.rs.gov.br',
				matricula: 12345,
				nome: 'Fulano Beltrano da Silva',
				nroSolicitacao: 23,
				vagas: ['Canoas 1', 'Canoas 2', 'Canoas 3'] as List<String>,
				dataSolicitacao: '31/07/2015 08:33:36',
				dataConfirmacao: '31/07/2015 08:33:45',
				)
		emailService.enviarReciboCandidatura(model, 101)
	}

	@Test
	void 'disparaEmailParaORecipienteOriginalQuandoOSpringProfilesActiveForProd'() {
		[
			[profiles: ['prod'] as String[], expected: ['email@test.com'] as String[]],
			[profiles: ['prod', 'outro_profile'] as String[], expected: ['email@test.com'] as String[]],
		].each { dataProvider ->
			def envMock = [getActiveProfiles: {->dataProvider.profiles}] as Environment
			emailService.environment = envMock
			assert emailService.resolveEmailRecipientes(['email@test.com'] as String[]) == dataProvider.expected
		}
	}

	@Test
	void 'naoDisparaEmailQuandoTesteEmailEnvioForTruePoremNaoFoiDefinidoTesteEmailCaixas'() {
		[[envProperties: ['teste.email.ativo': 'true', 'teste.email.caixas':''], expected: []]].each { dp ->
			def envMock = [
				getActiveProfiles: {-> ['junit'] as String[]},
				getRequiredProperty: {key -> dp.envProperties[key]}
			] as Environment
			emailService.environment = envMock
			assert emailService.resolveEmailRecipientes(['email@test.com'] as String[]) == dp.expected
		}
	}

	@Test
	void 'disparaEmailParaAsCaixasTesteEmailCaixasQuandoTesteEmailEnvioForTrue'() {
		[
			[envProperties: ['teste.email.ativo': 'true', 'teste.email.caixas':'email1@teste.com,email2@teste.com'], expected: ['email1@teste.com', 'email2@teste.com']]
		].each { dp ->
			def envMock = [
				getActiveProfiles: {-> ['junit'] as String[]},
				getRequiredProperty: {key -> dp.envProperties[key]}
			] as Environment
			emailService.environment = envMock
			assert emailService.resolveEmailRecipientes(['email@test.com'] as String[]) == dp.expected
		}
	}
}
