package com.qumasi.model.utility;

import com.qumasi.model.data.Messages;

public class IntegrityAuthentication {


    public static Messages prepareForSend(Messages m, String senderPrivateKey) {
        byte[] encriptedHash = SecurityUtility.encriptDataByPrivateKey(m.getHash(), senderPrivateKey);
        m.setHash(encriptedHash);
        return m;
    }

    public static Messages prepareForRecieve(Messages m, String senderPubicKey) {
        byte[] digist = m.getHash();
        byte[] decryptedHash = SecurityUtility.decriptDataByPublicKey(digist, senderPubicKey);
        byte[] generatedHash = SecurityUtility.hashing(m.getMessage());
        if(new String(decryptedHash).equals(new String(generatedHash)))
        System.out.println("IntegrityAuthentication=============Server====recieve valid hash :");
        else
            System.out.println("IntegrityAuthentication=============Server====recieve invalid hash:");
        m.setHash(decryptedHash);
        return m;
    }

}
