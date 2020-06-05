package com.qumasi.model.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.math.BigInteger;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyStore {

    public static PublicKey readPublicKeyFromFile(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();
            RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(rsaPubKey);
            return pubKey;

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeySpecException e) {
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                    if (fis != null)
                        fis.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }

    public static PrivateKey readPrivateKeyFromFile(String fileName) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(new File(fileName));
            ois = new ObjectInputStream(fis);
            BigInteger modulus = (BigInteger) ois.readObject();
            BigInteger exponent = (BigInteger) ois.readObject();
            RSAPrivateKeySpec rsaPrivKey = new RSAPrivateKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey privKey = factory.generatePrivate(rsaPrivKey);
            return privKey;

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeySpecException e) {
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                    if (fis != null)
                        fis.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }
}
