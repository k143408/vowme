package com.vowme.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * The Class AESEncryptionUtils.
 */
public class AESEncryptionUtils {
	
	/** The secret key. */
	private static SecretKeySpec secretKey;
    
    /** The key. */
    private static byte[] key;
    
    /** The Constant ALGO. */
    private static final String ALGO = "AES";
    
    /** The Constant constSecretKey. */
    private static final String constSecretKey = "Dpotu@ouLfz";
    
    
 
    /**
     * Sets the key.
     *
     * @param myKey the new key
     */
    private static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    /**
     * Encrypt.
     *
     * @param strToEncrypt the str to encrypt
     * @return the string
     */
    public static String encrypt(String strToEncrypt) {
    	if (null == strToEncrypt || "".equals(strToEncrypt)) return null;
        try {
            setKey(constSecretKey);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) {
        	
        }
        return null;
    }
 
    /**
     * Decrypt.
     *
     * @param strToDecrypt the str to decrypt
     * @return the string
     */
    public static String decrypt(String strToDecrypt) {
    	if (null == strToDecrypt || "".equals(strToDecrypt)) return null;
        
    	try {
            setKey(constSecretKey);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } 
        catch (Exception e) {
        	
        }
        return null;
    }
}