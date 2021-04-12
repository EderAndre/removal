package br.gov.rs.defensoria.removal.maestro.service

import org.junit.Before
import org.junit.Test

class CipherServiceTest {
	private CipherService cipher
	
	@Before
    void setup() {
		cipher = new CipherService()
	}

	@Test
    void 'testa cifragem e decifragem'() {
		String chave = "MZygpewJsCpRrfOr"
		String texto = 'TEXTO DE ENTRADA 1234 $%XXx_('
		String textoCifrado =  cipher.encrypt(chave, texto)
		
		assert cipher.decrypt(chave, textoCifrado) == texto
	}
}
