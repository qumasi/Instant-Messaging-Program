package com.qumasi.model.utility;


import com.qumasi.model.data.Messages;

public class IntegrityAuthenticationConfidentiality {
    public static Messages prepareForSend1(String message, String senderPrivateKey, String receiverPublicKey) {
        byte[] generatedHash = SecurityUtility.hashing(message);
        byte[] encriptedData = SecurityUtility.encriptDataByPublicKey(message, receiverPublicKey);
        byte[] encriptedHash = SecurityUtility.encriptDataByPrivateKey(generatedHash, senderPrivateKey);

        Messages messageToSend = new Messages();
        messageToSend.setHash(encriptedHash);
        messageToSend.setMessage(encriptedData);
        return messageToSend;
    }

    public static String prepareForRecieve1(Messages m, String senderPublicKey, String receiverPrivateKey) {
        byte[] decriptedHash = SecurityUtility.decriptDataByPublicKey(m.getHash(), senderPublicKey);


        byte[] decriptedData = SecurityUtility.decriptDataByPrivateKey(m.getMessage(), receiverPrivateKey);
        byte[] generatedHash = SecurityUtility.hashing(decriptedData);


        if(new String(generatedHash).equals(new String(decriptedHash)))
            System.out.println("=================Client recieved valid message===============");
        

        return new String(decriptedData);
    }


}
