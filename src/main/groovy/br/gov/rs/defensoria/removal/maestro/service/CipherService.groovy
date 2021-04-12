package br.gov.rs.defensoria.removal.maestro.service

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64
import org.springframework.stereotype.Component

@Component
class CipherService {
	String encrypt(String encryptionKey, String plainText) throws Exception {
		Cipher cipher = getCipher(encryptionKey, Cipher.ENCRYPT_MODE)
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes())

        return Base64.encodeBase64String(encryptedBytes)
    }

    String decrypt(String encryptionKey, String encrypted) throws Exception {
		Cipher cipher = getCipher(encryptionKey, Cipher.DECRYPT_MODE)
        byte[] plainBytes = cipher.doFinal(Base64.decodeBase64(encrypted))

        return new String(plainBytes)
    }

	private Cipher getCipher(String encryptionKey, int cipherMode) throws Exception {
		String encryptionAlgorithm = "AES"
        SecretKeySpec keySpecification = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), encryptionAlgorithm)
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm)
        cipher.init(cipherMode, keySpecification)

        return cipher
    }
}
