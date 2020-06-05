package com.qumasi.model.utility;

import com.qumasi.model.data.Messages;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AuthenticationConfidentiality {

    public static Messages prepareForSend(String messsage, String senderPrivateKey, String receiverPublicKey) {
        SecretKey generateSharedKey = SecurityUtility.generateSharedKey();
        byte[] encriptedDataBySenderPrivateKey =
            SecurityUtility.encriptDataByPrivateKey(messsage, senderPrivateKey);
        byte[] encriptedDataBySharedKey =
            SecurityUtility.encriptBySharedKey(encriptedDataBySenderPrivateKey, generateSharedKey);
        byte[] encriptedSharedKey =
            SecurityUtility.encriptDataByPublicKey(generateSharedKey.getEncoded(), receiverPublicKey);
        Messages m=new Messages();
        m.setMessage(encriptedDataBySharedKey);
        m.setKey(encriptedSharedKey);
        m.setHash(generateSharedKey.getEncoded());
        return m;
    }

    public static String prepareForRecieve(Messages m, String senderPublicKey) {     
        SecretKey sharedKey=new SecretKeySpec(m.getKey(), 0, m.getKey().length,"AES");
        byte[] decriptedDatyaBySharedKey = SecurityUtility.decriptBySharedKey(m.getMessage(), sharedKey);
        System.out.println("decriptedDatyaBySharedKey====== "+decriptedDatyaBySharedKey);
        byte[] decriptedDatyaBySenderPublicKey =
            SecurityUtility.decriptDataByPublicKey(decriptedDatyaBySharedKey, senderPublicKey);
        m.setMessage(decriptedDatyaBySenderPublicKey);
        System.out.println("AuthenticationConfidentiality===============Client=========recieve > "+decriptedDatyaBySenderPublicKey);
        return new String(decriptedDatyaBySenderPublicKey);
    }

}
