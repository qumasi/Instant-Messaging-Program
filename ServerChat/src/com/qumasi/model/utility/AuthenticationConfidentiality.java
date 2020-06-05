package com.qumasi.model.utility;

import com.qumasi.model.data.Messages;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AuthenticationConfidentiality {
    public static Messages prepareForRecieve(Messages m, String senderPublicKey, String receiverPrivateKey) {
        byte[] decriptedSharedKey = SecurityUtility.decriptDataByPrivateKey(m.getKey(), receiverPrivateKey);
        System.out.println("decriptedSharedKey==========Server=====receive=====> "+new String(decriptedSharedKey));
        SecretKey sharedKey=new SecretKeySpec(decriptedSharedKey, 0, decriptedSharedKey.length,"AES");
        byte[] decriptedDatyaBySharedKey = SecurityUtility.decriptBySharedKey(m.getMessage(), sharedKey);
        System.out.println("decriptedDatyaBySharedKey==========Server=====receive=====> "+new String(decriptedDatyaBySharedKey));
        byte[] decriptedDatyaBySenderPublicKey =
            SecurityUtility.decriptDataByPublicKey(decriptedDatyaBySharedKey, senderPublicKey);
        
        m.setKey(sharedKey.getEncoded());
        m.setMessage(decriptedDatyaBySenderPublicKey);
        System.out.println("AuthenticationConfidentiality==========Server=====receive=====> "+new String(decriptedDatyaBySenderPublicKey));

        return m;
    }

    public static byte[] prepareForSend(Messages messsage, String senderPrivateKey) {
        System.out.println("SecretKey==========Server=====receive=====> "+new String(messsage.getKey()));
        SecretKey sharedKey=new SecretKeySpec(messsage.getKey(), 0, messsage.getKey().length,"AES");
        byte[] encriptedDataBySenderPrivateKey =
            SecurityUtility.encriptDataByPrivateKey(messsage.getMessage(), senderPrivateKey);
        byte[] encriptedDataBySharedKey =
            SecurityUtility.encriptBySharedKey(encriptedDataBySenderPrivateKey, sharedKey);
        System.out.println("encriptedDataBySharedKey==========Server=====receive=====> "+new String(encriptedDataBySharedKey));
        return encriptedDataBySharedKey;
    }
}
