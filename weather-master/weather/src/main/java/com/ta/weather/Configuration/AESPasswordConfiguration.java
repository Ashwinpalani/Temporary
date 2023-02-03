package com.ta.weather.Configuration;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.Key;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AESPasswordConfiguration {
	static Logger log = LogManager.getLogger("P");
	private static final String ALGORITHM = "AES";
	private static final String myEncryptionKey = "ThisIsFoundation";
	private static final String UNICODE_FORMAT = "UTF-8";

	public static String encrypt(String valueToEnc) {
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.ENCRYPT_MODE, key);
			// byte[] encValue = c.doFinal(valueToEnc.getBytes());
			byte[] encValue = c.doFinal(valueToEnc.getBytes(UNICODE_FORMAT));
			String encryptedValue = Base64.getEncoder().withoutPadding().encodeToString(encValue);
			return encryptedValue;
		} catch (Exception ex) {
			return "NA";
		}

	}

	public static String decrypt(String encryptedValue) {
		try {
			Key key = generateKey();
			Cipher c = Cipher.getInstance(ALGORITHM);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decordedValue = Base64.getDecoder().decode(encryptedValue);
			byte[] decValue = c.doFinal(decordedValue);
			String decryptedValue = new String(decValue);
			return decryptedValue;
		} catch (BadPaddingException e) {
			StringWriter sw = new StringWriter();
	        e.printStackTrace(new PrintWriter(sw));
			log.error(e.toString());
			return "Error - ";
		} catch (Exception ex) {
			StringWriter sw = new StringWriter();
	        ex.printStackTrace(new PrintWriter(sw));
			log.error(ex.toString());
			return "Error - Exception";
		}
	}

	private static Key generateKey() throws Exception {
		byte[] keyAsBytes;
		keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
		Key key = new SecretKeySpec(keyAsBytes, ALGORITHM);
		return key;
	}

}