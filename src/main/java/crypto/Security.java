package crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class Security {

	private static SecretKeySpec secretKey;
	private static byte[] key;
	static String secKey = "user@123";
	
	public static void setKey( String myKey) {
		MessageDigest sha = null;

		try {
			key = myKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		key = sha.digest(key);
		key = Arrays.copyOf(key, 32);
		secretKey = new SecretKeySpec(key, "AES");

	}

	public static String encrypt(final String strToEncrypt) {
		try {
			setKey(secKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(final String strToDecrypt) {
		try {
			setKey(secKey);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

/*
	 * public static void main(String[] args) {
	 *
	 *
	 * String originalString = "howtodoinjava.com"; String encryptedString =
	 * AES.encrypt(originalString, secretKey); String decryptedString =
	 * AES.decrypt(encryptedString, secretKey);
	 *
	 * System.out.println(originalString); System.out.println(encryptedString);
	 * System.out.println(decryptedString); }
*/

}
