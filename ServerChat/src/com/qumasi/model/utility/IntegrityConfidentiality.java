package com.qumasi.model.utility;


import com.qumasi.model.data.Messages;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class IntegrityConfidentiality {
    public static Messages prepareForRecieveAndSend(Messages message, byte[] sharedKey, String recieverPrivateKey,
                                                    String senderPublicKey) {
        Messages m = new Messages();
        SecretKey secKey = new SecretKeySpec(sharedKey, 0, sharedKey.length, "AES");
        byte[] decriptedMessage = SecurityUtility.decriptBySharedKey(message.getMessage(), secKey);
        System.out.println("decriptedMessage=========================== " + new String(decriptedMessage));

        byte[] decriptedHash = SecurityUtility.decriptDataByPrivateKey(message.getHash(), recieverPrivateKey);
        byte[] generatedHash = SecurityUtility.hashing(decriptedMessage);

        if (new String(decriptedHash).equals(new String(generatedHash))) {
            System.out.println("IntegrityConfidentiality=================server received valid message========= " +
                               new String(decriptedMessage));
        } else
            System.out.println("IntegrityConfidentiality=================server received invalid message========= " +
                               new String(decriptedMessage));
        byte[] encriptedData = SecurityUtility.encriptBySharedKey(decriptedMessage, secKey);
        byte[] encriptedHash = SecurityUtility.encriptDataByPublicKey(decriptedHash, senderPublicKey);
        m.setMessage(encriptedData);
        m.setHash(encriptedHash);
        return m;
    }
}
