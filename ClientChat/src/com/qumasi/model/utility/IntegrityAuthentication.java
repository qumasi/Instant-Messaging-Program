package com.qumasi.model.utility;

import com.qumasi.model.data.Messages;

public class IntegrityAuthentication {

    public static Messages prepareForSend(String message, String senderPrivateKey) {
        Messages messageToSend = new Messages();
        byte[] generatedHash = SecurityUtility.hashing(message);
        byte[] encriptedHash = SecurityUtility.encriptDataByPrivateKey(generatedHash, senderPrivateKey);

        messageToSend.setHash(encriptedHash);
        messageToSend.setMessage(message.getBytes());
        return messageToSend;
    }

    public static String prepareForRecieve(Messages m, String senderPubicKey) {
        byte[] encryptedHash = m.getHash();
        byte[] decryptedHash = SecurityUtility.decriptDataByPublicKey(encryptedHash, senderPubicKey);
        byte[] generatedHash = SecurityUtility.hashing(m.getMessage());
        if (new String(decryptedHash).equals(new String(generatedHash)))
            return new String(m.getMessage());
        else
            return "Integirty Error";
    }

}
