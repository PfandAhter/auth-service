package com.hotelreservation.authservice.authentication;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtil {

    public static String decrypt(String encryptedValue, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(encryptedValue));
        return new String(plainText);
    }

    public static String encrypt(String value, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Key key = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(value.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    private static Key generateKey(String keyValue) {
        return new SecretKeySpec(keyValue.getBytes(StandardCharsets.UTF_8), "AES");
    }
}
