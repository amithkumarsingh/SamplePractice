package com.vam.whitecoats.utils;

/**
 * Created by venuv on 12/27/2017.
 */

import android.util.Base64;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


public class DESedeEncryption {
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec myKeySpec;
    private SecretKeyFactory mySecretKeyFactory;
    private Cipher cipher;
    byte[] keyAsBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;
    private static DESedeEncryption instance=null;

    public DESedeEncryption() throws Exception
    {
        myEncryptionKey = "934e1f5bd3f178601c3950ba";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        myKeySpec = new DESedeKeySpec(keyAsBytes);
        mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = mySecretKeyFactory.generateSecret(myKeySpec);
    }

    /**
     * Method To Encrypt The String
     */
    public String encrypt(String unencryptedString) {
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            //BASE64Encoder base64encoder = new BASE64Encoder();
            encryptedString = Base64.encodeToString(encryptedText, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    /**
     * Method To Decrypt An Ecrypted String
     */
   /* public String decrypt(String encryptedString) {
        String decryptedText=null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            //BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] encryptedText = Base64.decode(encryptedString,Base64.DEFAULT);
            String base64Decode = new String(encryptedText);
//            java.util.Base64.getDecoder().decode(encryptedString);
            byte[] plainText = cipher.doFinal(base64Decode.getBytes(UNICODE_FORMAT));
            decryptedText= bytes2String(plainText);


            String decoding = base64Decoding(encryptedString);
            objectIntialization();
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(decoding);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = bytes2String(plainText);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }*/

    public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            /*String decoding = base64Decoding(encryptedString);
            objectIntialization();
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.getDecoder().decode(decoding);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = bytes2String(plainText);*/

            String decoding = base64Decoding(encryptedString);
            //objectIntialization();
            cipher.init(Cipher.DECRYPT_MODE, key);
            //BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] encryptedText = Base64.decode(decoding,Base64.DEFAULT);
            //String base64Decode = new String(encryptedText);
//            java.util.Base64.getDecoder().decode(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText= bytes2String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

    private static String base64Decoding(String desString) {
        String base64Decode = bytes2String(Base64.decode(desString,Base64.DEFAULT));
        return base64Decode;
    }
    /**
     * Returns String From An Array Of Bytes
     */
    private static String bytes2String(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append((char) bytes[i]);
        }
        return stringBuffer.toString();
    }

    public static DESedeEncryption getInstance() {
        if(instance == null) {
            try {
                instance = new DESedeEncryption();
            } catch (Exception e) {
                instance=null;
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Testing The DESede Encryption And Decryption Technique
     */
    /*public static void main(String args []) throws Exception
    {
        DESedeEncryption myEncryptor= new DESedeEncryption();

        String stringToEncrypt="[21234,23232,2232232000]";
        String encrypted=myEncryptor.encrypt(stringToEncrypt);
        String decrypted=myEncryptor.decrypt(encrypted);

        System.out.println("String To Encrypt: "+stringToEncrypt);
        System.out.println("Encrypted Value :" + encrypted);
        System.out.println("Decrypted Value :"+decrypted);

    }*/

}
