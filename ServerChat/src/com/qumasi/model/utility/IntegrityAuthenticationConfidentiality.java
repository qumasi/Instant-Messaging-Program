package com.qumasi.model.utility;


import com.qumasi.model.data.Messages;

public class IntegrityAuthenticationConfidentiality {
  
    public static Messages prepareForSend1(Messages m, String senderPrivateKey, String receiverPublicKey) {
        System.out.println("=========================================="+m.getMessage());
        byte[] generatedHash = SecurityUtility.hashing(m.getMessage());
        
        byte[] encriptedData = SecurityUtility.encriptDataByPublicKey(m.getMessage(), receiverPublicKey);
        
        byte[] encriptedHash = SecurityUtility.encriptDataByPrivateKey(generatedHash, senderPrivateKey);
        
        
        
        m.setHash(encriptedHash);
        m.setMessage(encriptedData);
        

        return m;
    }

    public static Messages prepareForRecieve1(Messages m, String senderPublicKey, String recieverPrivateKey) {
        byte[] decriptedHash = SecurityUtility.decriptDataByPublicKey(m.getHash(), senderPublicKey);
        

        byte[] decriptedData = SecurityUtility.decriptDataByPrivateKey(m.getMessage(), recieverPrivateKey);
        byte[] generatedHash = SecurityUtility.hashing(decriptedData);


        if(new String(generatedHash).equals(new String(decriptedHash)))
            System.out.println("=================Server recieve valid message===============");
        

        m.setHash(decriptedHash);
        m.setMessage(decriptedData);

        return m;
    }

    
}
