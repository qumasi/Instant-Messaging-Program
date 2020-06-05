package com.qumasi.model.utility;


public class Confidentiality {
   
    public static byte[] prepareForSend(String message, String receiverPublicKey) {
        byte[] encriptedData = SecurityUtility.encriptDataByPublicKey(message.getBytes(), receiverPublicKey);
        return encriptedData;
    }

    public static String prepareForRecieve(byte[] message, String receiverPrivateKey) {
        byte[] decriptedData = SecurityUtility.decriptDataByPrivateKey(message, receiverPrivateKey);
        return new String(decriptedData);
    }
}
