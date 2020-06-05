package com.qumasi.model.utility;


import com.qumasi.model.data.Messages;

public class Authentication {
    
    public static Messages prepareForSend(String message, String senderPrivateKey) {
        Messages m=new Messages();
        byte[] generatedHash = SecurityUtility.hashing(message.getBytes());
        byte[] encriptedHash = SecurityUtility.encriptDataByPrivateKey(generatedHash, senderPrivateKey);
        m.setMessage(message.getBytes());
        m.setHash(encriptedHash);
        return m;
    }

    public static String prepareForRecieve(Messages m, String senderPublicKey) {
        byte[] encryptedHash = m.getHash();
        byte[] decryptedHash = SecurityUtility.decriptDataByPublicKey(encryptedHash, senderPublicKey);
        m.setHash(decryptedHash);
        return new String(m.getMessage());
    }
}
