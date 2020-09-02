package com.billing.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

@Service
public class EncryptDecryptUtils {

	private static final String PASS_PHRASE = "THIS$is!JAVA$and#Java&IS^BEST";
	private static final byte[] SALT_BYTE = "JAVA/ORACLE".getBytes();
	private static final String UNICODE_FORMAT = "UTF-8";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final int iterations = 15000;
	private static final String SECRET_FACTORY_ALGORITHM = "PBKDF2WithHmacSHA1";
	
    private static Cipher cipher;
    private static SecretKeySpec serkey; 
    private static final byte[] IV = { 0, 10, 20, 80, 50, 30, 90, 70, 80, 50, 60, 80, 04, 05, 03, 05 };
    private static final IvParameterSpec IVSPEC = new IvParameterSpec(IV);

	static{
		try {

			SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_FACTORY_ALGORITHM);
			SecretKey tmp = factory.generateSecret(new PBEKeySpec(PASS_PHRASE.toCharArray(), SALT_BYTE, iterations, 128));
			serkey = new SecretKeySpec(tmp.getEncoded(), "AES");
			cipher = Cipher.getInstance(TRANSFORMATION);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static synchronized String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, serkey, IVSPEC);
			byte[] encryptedText = cipher.doFinal(unencryptedString.getBytes(UNICODE_FORMAT));
			encryptedString = Base64.encodeBase64URLSafeString(encryptedText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public static synchronized String decrypt(String encryptedString) {
		String decryptedText=null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, serkey, IVSPEC);
			byte[] encryptedText = Base64.decodeBase64(encryptedString.getBytes());
			decryptedText = new String(cipher.doFinal(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
}
