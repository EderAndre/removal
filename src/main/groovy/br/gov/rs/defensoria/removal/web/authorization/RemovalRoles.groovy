package br.gov.rs.defensoria.removal.web.authorization

class RemovalRoles  {

	public static final String SUPER_USER = "SUPER_USER"
	public static final String OPERADOR = "OPERADOR"
	public static final String CANDIDATO = "CANDIDATO"

	public static final String AUTHENTICATED = "hasAnyRole('ROLE_SUPER_USER','ROLE_OPERADOR', 'ROLE_CANDIDATO')"

	public static final String IS_SUPER_USER = "hasRole('ROLE_SUPER_USER')"
	public static final String IS_OPERADOR = "hasRole('ROLE_OPERADOR')"
	public static final String IS_CANDIDATO = "hasRole('ROLE_CANDIDATO')"

}
