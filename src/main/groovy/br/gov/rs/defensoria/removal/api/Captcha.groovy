package br.gov.rs.defensoria.removal.api

class Captcha {
	/*
	 * Contém o valor da resposta encriptada que deve ser conferida com o valor pelo usuário na propriedade: codigo
	 * */
	public String segredo
	
	/*
	 * Valor fornecido pelo usuário
	 * */
	public String codigo
}
