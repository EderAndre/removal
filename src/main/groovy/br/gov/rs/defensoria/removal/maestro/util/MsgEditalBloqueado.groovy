package br.gov.rs.defensoria.removal.maestro.util

enum MsgEditalBloqueado {

	EDITAL_INEXISTENTE_PARA_O_CODIGO("Edital inexistente para o código %s."),
	EDITAL_BLOQUEADO_PELO_ADMINISTRADOR("Edital bloqueado pelo administrador."),
	EDITAL_JA_ENCERRADO("Edital já encerrado em %s."),
	EDITAL_AINDA_NAO_ABRIU("Este edital abrirá somente em %s."),
	SEM_CANDIDATURA_ATE_DATA_LIMITE("Prazo encerrado para candidaturas no edital %s.")

	private String msg

	MsgEditalBloqueado(String msg) {
		this.msg = msg
	}

    String getMsg() {
		return msg
	}
}
