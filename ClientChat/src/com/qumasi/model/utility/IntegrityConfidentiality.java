package com.qumasi.model.utility;


import com.qumasi.model.data.Messages;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class IntegrityConfidentiality {  

    public static Messages prepareForSend3(String message, byte[] sharedKey,String recieverPublicKey) {
        Messages m = new Messages();
        SecretKey secKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] generatedHash = SecurityUtility.hashing(message.getBytes());
        byte[] encriptedData = SecurityUtility.encriptBySharedKey(message.getBytes(), secKey);
        byte[] encriptedHash = SecurityUtility.encriptDataByPublicKey(generatedHash, recieverPublicKey);
        m.setMessage(encriptedData);
        m.setHash(encriptedHash);
        return m;
    }

    public static String prepareForRecieve3(Messages message, byte[] sharedKey,String recieverPrivateKey) {
        Messages m = null;
        SecretKey decodedSharedKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] decriptedMessage = SecurityUtility.decriptBySharedKey(message.getMessage(), decodedSharedKey);
        byte[] generatedHash = SecurityUtility.hashing(decriptedMessage);
        byte[] decriptedHash = SecurityUtility.decriptDataByPrivateKey(message.getHash(), recieverPrivateKey);

        System.out.println(new String(decriptedHash)+"====================="+(new String(generatedHash)));
        System.out.println(new String(decriptedHash).equals(new String(generatedHash)));
        return new String(decriptedMessage);
    }
}
