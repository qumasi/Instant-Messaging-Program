package com.qumasi.model.utility;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SecurityUtility {

    public static byte[] hashing(String data) {
        String dataToHash = data;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(dataToHash.getBytes());
            byte[] digest = md.digest();
            return digest;
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static byte[] hashing(byte[] data) {
        byte[] dataToHash = data;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(dataToHash);
            byte[] digest = md.digest();
            System.out.println(new String(digest));
            return digest;
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static byte[] encriptDataByPublicKey(String data, String publicKey) {
        byte[] dataToEncrpt = data.getBytes();
        byte[] encrptedData = null;
        try {
            PublicKey pubKey = KeyStore.readPublicKeyFromFile(publicKey);
            Cipher cipher;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encrptedData = cipher.doFinal(dataToEncrpt);
        } catch (NoSuchPaddingException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        }
        return encrptedData;
    }

    public static byte[] encriptDataByPrivateKey(String data, String privateKey) {
        byte[] dataToEncrpt = data.getBytes();
        byte[] encrptedData = null;
        try {
            PrivateKey priKey = KeyStore.readPrivateKeyFromFile(privateKey);
            Cipher cipher;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, priKey);
            encrptedData = cipher.doFinal(dataToEncrpt);
        } catch (NoSuchPaddingException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        }
        return encrptedData;
    }

    public static byte[] decriptDataByPublicKey(String data, PublicKey pubKey) {
        byte[] dataToDecrpt = data.getBytes();
        byte[] decrptedData = null;
        try {
            //PublicKey pubKey = KeyStore.readPublicKeyFromFile(publicKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            decrptedData = cipher.doFinal(dataToDecrpt);
        } catch (NoSuchPaddingException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        }
        return decrptedData;
    }

    public static byte[] decriptDataByPrivateKey(String data, String privateKey) {
        byte[] dataToDecrpt = data.getBytes();
        byte[] decrptedData = null;
        try {
            PrivateKey priKey = KeyStore.readPrivateKeyFromFile(privateKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            decrptedData = cipher.doFinal(dataToDecrpt);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decrptedData;
    }


    public static byte[] decriptDataByPrivateKey(byte[] data, String privateKey) {
        byte[] dataToDecrpt = data;
        byte[] decrptedData = null;
        try {
            PrivateKey priKey = KeyStore.readPrivateKeyFromFile(privateKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            decrptedData = cipher.doFinal(dataToDecrpt);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decrptedData;
    }

    public static byte[] decriptDataByPublicKey(byte[] data, PublicKey pubKey) {
        byte[] dataToDecrpt = data;
        byte[] decrptedData = null;
        try {
            //PublicKey pubKey = KeyStore.readPublicKeyFromFile(publicKey);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            decrptedData = cipher.doFinal(dataToDecrpt);
        } catch (NoSuchPaddingException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeyException e) {
        } catch (BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        }
        return decrptedData;
    }

    public static byte[] decriptDataByPublicKey(byte[] dataToDecrpt, String publicKey) {
        byte[] decrptedData = null;
        try {
            PublicKey pubKey = KeyStore.readPublicKeyFromFile(publicKey);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, pubKey);
            decrptedData = cipher.doFinal(dataToDecrpt);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return decrptedData;
    }

    public static byte[] encriptDataByPublicKey(byte[] data, String publicKey) {
        byte[] dataToEncrpt = data;
        byte[] encrptedData = null;
        try {
            PublicKey pubKey = KeyStore.readPublicKeyFromFile(publicKey);
            Cipher cipher;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encrptedData = cipher.doFinal(dataToEncrpt);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return encrptedData;
    }

    public static byte[] encriptDataByPrivateKey(byte[] dataToEncrpt, String privateKey) {
        byte[] encrptedData = null;
        try {
            PrivateKey priKey = KeyStore.readPrivateKeyFromFile(privateKey);
            Cipher cipher;
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, priKey);
            encrptedData = cipher.doFinal(dataToEncrpt);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return encrptedData;
    }

    public static SecretKey generateSharedKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128); // The AES key size in number of bits
            SecretKey secKey = generator.generateKey();
            return secKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encriptBySharedKey(byte[] dataToEncript, SecretKey secKey) {
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
            byte[] encriptedData = aesCipher.doFinal(dataToEncript);
            return encriptedData;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decriptBySharedKey(byte[] dataToDecript, SecretKey secKey) {
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secKey);
            byte[] decriptedData = aesCipher.doFinal(dataToDecript);
            return decriptedData;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
