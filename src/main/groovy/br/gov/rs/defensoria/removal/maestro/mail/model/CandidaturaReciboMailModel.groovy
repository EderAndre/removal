package br.gov.rs.defensoria.removal.maestro.mail.model

class CandidaturaReciboMailModel {
	public String arquivoTemplate = '/templates/mail/recibo-candidatura.tpl'
	public String assunto = 'Defensoria Pública - Habilitação à Remoção'

	public String email
	public String matricula
	public String nome
	public int nroSolicitacao
	public List<String> vagas
	public String dataSolicitacao
	public String dataConfirmacao

    String template() {
		return this.getClass().getResource(arquivoTemplate).getText("UTF-8")
	}
}
