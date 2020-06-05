package com.qumasi.model.utility;

import com.qumasi.model.data.Challenge;
import com.qumasi.model.data.Messages;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Integrity {
   
    public static Messages prepareToSendChallenge(String recievePublicKey) {
        Messages m = new Messages();
        Challenge challenge=new Challenge();
        SecretKey generateSharedKey = SecurityUtility.generateSharedKey();

        challenge.setChellange("hello".getBytes());
        challenge.setSecritKey(generateSharedKey.getEncoded());

        byte[] encodedChallenge = Utility.convertObjectToBytes(challenge);
        byte[] encriptedChallenge = SecurityUtility.encriptDataByPublicKey(encodedChallenge, recievePublicKey);
        
        
        m.setMessage(encriptedChallenge);
        m.setKey(generateSharedKey.getEncoded());
        
        return m;
    }

    public static boolean prepareToRecieveChallenge(byte[] challenge, byte[] sharedKey) {
        SecretKey secKey = (SecretKey) Utility.convertBytesToObject(sharedKey);
        byte[] decriptChallenge = SecurityUtility.decriptBySharedKey(challenge, secKey);
        System.out.println("==================== decriptChallenge  : "+new String(decriptChallenge));
        if ((new String(decriptChallenge)).equals("hello"))
            return true;
        else
            return false;
    }
    public static boolean prepareToReceiveChallenge3(byte[] encriptedChallenge, byte[] sharedKey) {
        SecretKey decodedSharedKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] decriptedChallenge = SecurityUtility.decriptBySharedKey(encriptedChallenge, decodedSharedKey);
        System.out.println("=====================> "+new String(decriptedChallenge));
        if ((new String(decriptedChallenge)).equals("hello"))
            return true;
        else
            return false;
        //return decriptedChallenge;
    }


    public static Messages prepareForSend3(String message, byte[] sharedKey) {
        Messages m = new Messages();
        SecretKey encodedSharedKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] generatedHash = SecurityUtility.hashing(message.getBytes());
        byte[] encriptedHash = SecurityUtility.encriptBySharedKey(generatedHash, encodedSharedKey);
        byte[] decriptedHash = SecurityUtility.decriptBySharedKey(encriptedHash, encodedSharedKey);
        System.out.println(decriptedHash+"============dddd=============="+generatedHash);
        m.setMessage(message.getBytes());
        m.setHash(encriptedHash);
        return m;
    }

    public static Messages prepareForRecieve3(Messages message, byte[] sharedKey) {
        Messages m = null;
        SecretKey decodedSharedKey= new SecretKeySpec(sharedKey, 0, sharedKey.length,"AES");
        byte[] generatedHash = SecurityUtility.hashing(message.getMessage());
        byte[] decriptedHash = SecurityUtility.decriptBySharedKey(message.getHash(), decodedSharedKey);
        System.out.println(new String(decriptedHash)+"====================="+(new String(generatedHash)));
        System.out.println(new String(decriptedHash).equals(new String(generatedHash)));
        if(new String(decriptedHash).equals(new String(generatedHash)))
            return message;
        return m;
    }

}
