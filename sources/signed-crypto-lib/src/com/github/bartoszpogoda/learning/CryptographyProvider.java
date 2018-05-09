package com.github.bartoszpogoda.learning;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CryptographyProvider {

	private final String USED_ALGORITHM = "AES";

	private final String key;

	private CryptographyProvider(String key) {
		this.key = key;
	}

	public byte[] decode(byte[] bytes) throws Exception {
		checkPermission();
		
		Key secretKey = new SecretKeySpec(key.getBytes(), USED_ALGORITHM);

		Cipher cipher = Cipher.getInstance(USED_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}

	public byte[] encode(byte[] bytes) throws Exception {
		checkPermission();
		
		Key secretKey = new SecretKeySpec(key.getBytes(), USED_ALGORITHM);

		Cipher cipher = Cipher.getInstance(USED_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(bytes);
	}
	
	private void checkPermission() {
		SecurityManager sm = System.getSecurityManager();
		
		if(sm != null) {
			sm.checkPermission(new CryptographyProviderPermission("Crypto"));
		}
	}

	public static CryptographyProvider withSecretKey(String key) {
		return new CryptographyProvider(key);
	}
}
