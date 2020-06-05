package com.qumasi.model.utility;

import com.qumasi.model.data.Challenge;
import com.qumasi.model.data.Messages;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Integrity {

    public static byte[] prepareToSendChallenge(byte[] challenge, byte[] secKey) {
        SecretKey secretKey= new SecretKeySpec(secKey, 0, secKey.length,"AES");
        byte[] encriptedChallenge = SecurityUtility.encriptBySharedKey(challenge, secretKey);
        System.out.println("========================"+new String(encriptedChallenge));
        return encriptedChallenge;
    }

    public static Challenge prepareToRecieveChallenge(byte[] challenge, String recievePrivateKey) {
        System.out.println(challenge);
        byte[] decriptChallenge = SecurityUtility.decriptDataByPrivateKey(challenge, recievePrivateKey);
        Challenge challenge1 = (Challenge) Utility.convertBytesToObject(decriptChallenge);
        return challenge1;
    }


    public static Messages prepareForSend3(String message, byte[] sharedKey) {
        Messages m = new Messages();
        //SecretKey encodedSharedKey = (SecretKey) Utility.convertBytesToObject(sharedKey);
        SecretKey encodedSharedKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] generatedHash = SecurityUtility.hashing(message.getBytes());
        byte[] encriptedHash = SecurityUtility.encriptBySharedKey(generatedHash, encodedSharedKey);
        m.setMessage(message.getBytes());
        m.setHash(encriptedHash);
        return m;
    }

    public static Messages prepareForRecieve(Messages message, byte[] sharedKey) {
        Messages m = new Messages();
         //SecretKey encodedSharedKey = (SecretKey) Utility.convertBytesToObject(sharedKey);
        SecretKey encodedSharedKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] generatedHash = SecurityUtility.hashing(message.getMessage());
        byte[] decriptedHash = SecurityUtility.decriptBySharedKey(message.getHash(), encodedSharedKey);
        return message;
        /*  if (generatedHash == decriptedHash) {
            return message;
        } else {
            message.setMessage(null);
            return message;
        } */
    }

    public static byte[] prepareToReceiveChallenge3(byte[] encriptedChallenge, byte[] sharedKey) {
        SecretKey decodedSharedKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] decriptedChallenge = SecurityUtility.decriptBySharedKey(encriptedChallenge, decodedSharedKey);
        System.out.println("=====================> "+new String(decriptedChallenge));
        return decriptedChallenge;
    }
}
