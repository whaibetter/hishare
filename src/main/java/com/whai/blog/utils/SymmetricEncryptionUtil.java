package com.whai.blog.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @version 1.0
 * @Author whai文海
 * @Date 2024/4/1 16:36
 * @注释
 */

public class SymmetricEncryptionUtil {



    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "123456"; // 替换为你自己的密钥，密钥长度必须符合算法要求

    public static byte[] encrypt(byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedData);
    }

    public static void main(String[] args) throws Exception {
        SecretKey ts = KeyGenerator.getInstance("ts").generateKey();
        byte[] encrypt = SymmetricEncryptionUtil.encrypt("123456".getBytes());
        System.out.println(new String(encrypt));
        byte[] decrypt = SymmetricEncryptionUtil.decrypt(encrypt);
        System.out.println(new String(decrypt));

    }
}

