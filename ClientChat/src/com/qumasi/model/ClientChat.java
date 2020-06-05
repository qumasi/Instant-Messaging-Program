package com.qumasi.model;

import com.qumasi.model.data.LoginInfor;
import com.qumasi.model.data.Messages;
import com.qumasi.model.utility.Authentication;
import com.qumasi.model.utility.AuthenticationConfidentiality;
import com.qumasi.model.utility.Confidentiality;
import com.qumasi.model.utility.Integrity;
import com.qumasi.model.utility.IntegrityAuthentication;
import com.qumasi.model.utility.IntegrityAuthenticationConfidentiality;
import com.qumasi.model.utility.IntegrityConfidentiality;
import com.qumasi.model.utility.SecurityUtility;
import com.qumasi.model.utility.Utility;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;

public class ClientChat {
    private Socket socket = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private LoginInfor user = null;
    private byte[] sharedKey = null;


    public void openConnection() {
        try {
            socket = new Socket("localhost", 7777);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("3. Client connected");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }
    }

    public Object login(LoginInfor user) {
        try {
            this.user = user;
            byte[] bytes = Utility.convertObjectToBytes(this.user);
            byte[] byPublicKey = SecurityUtility.encriptDataByPublicKey(bytes, "Server_Public.key");
            System.out.println("========================" + byPublicKey.length);
            oos.writeObject(byPublicKey);
            oos.flush();
            Object object = ois.readObject();
            return object;
        } catch (UnknownHostException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }


    public String sendingMessageWithNoSecurity(String clientMessage) {
        try {
            System.out.println(clientMessage);
            oos.writeObject(clientMessage);
            oos.flush();
            return ois.readUTF();
        } catch (IOException e) {
        }
        return null;
    }

    public String sendingWithAuth(String clientMessage) {
        try {
            Messages message =
                Authentication.prepareForSend(clientMessage,
                                              "Client_Private_" + (new String(user.getUsername())) + ".key");
            oos.writeObject(message);
            oos.flush();
            String recievedMessage = Authentication.prepareForRecieve((Messages) ois.readObject(), "Server_Public.key");
            return recievedMessage;
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public String sendingWithConfid(String clientMessage) {
        try {
            byte[] message = Confidentiality.prepareForSend(clientMessage, "Server_Public.key");
            oos.writeObject(message);
            oos.flush();
            String recievedMessage =
                Confidentiality.prepareForRecieve((byte[]) ois.readObject(),
                                                  "Client_Private_" + new String(user.getUsername()) + ".key");
            return recievedMessage;
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public Messages sendingWithIntegrity(String clientMessage) {
        try {
            if (this.sharedKey == null)
                this.sharedKey = sendingChallenge();
            System.out.println(sharedKey);
            Messages message = Integrity.prepareForSend3(clientMessage, sharedKey);
            oos.writeObject(message);
            oos.flush();


            Messages recievedMessage = (Messages) ois.readObject();
            Messages m = Integrity.prepareForRecieve3(recievedMessage, sharedKey);
            return m;
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public byte[] sendingChallenge() {
        try {
            //------------------------step1
            Messages m = Integrity.prepareToSendChallenge("Server_Public.key");
            byte[] encriptedChallenge = m.getMessage();
            oos.writeObject(encriptedChallenge);
            oos.flush();

            //------------------------step2
            byte[] challenge = (byte[]) ois.readObject();
            System.out.println("================= null problem:  " + m.getKey());
            boolean isCorrectChallenge = Integrity.prepareToReceiveChallenge3(challenge, m.getKey());
            System.out.println(isCorrectChallenge);

            if (isCorrectChallenge)
                return m.getKey();

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public String sendingWithAuthConfid(String clientMessage) {
        try {
            
            Messages message = AuthenticationConfidentiality.prepareForSend(clientMessage,"Client_Private_" + new String(user.getUsername()) + ".key", "Server_Public.key");
            byte[] sharedKey=message.getHash();
            message.setHash(null);
            oos.writeObject(message);
            oos.flush();

            byte[] recievedMessage =(byte[]) ois.readObject();
            message.setMessage(recievedMessage);
            message.setKey(sharedKey);
            String decriptedMessage=AuthenticationConfidentiality.prepareForRecieve(message, "Server_Public.key");
            
            return decriptedMessage;
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public String sendingWithAuthIntegrity(String clientMessage) {
        
        try {
            
           Messages messageToSend = IntegrityAuthentication.prepareForSend(clientMessage,"Client_Private_" + new String(user.getUsername()) + ".key");
           oos.writeObject(messageToSend);
           oos.flush();

            Messages recievedMessage =(Messages) ois.readObject();

            String message=IntegrityAuthentication.prepareForRecieve(recievedMessage, "Server_Public.key");
            return message;
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public String sendingWithIntegrityConfid(String clientMessage) {
        try {
            if (this.sharedKey == null)
                this.sharedKey = sendingChallenge();
            System.out.println(sharedKey);
            Messages message = IntegrityConfidentiality.prepareForSend3(clientMessage, sharedKey,"Server_Public.key");
            oos.writeObject(message);
            oos.flush();


            Messages recievedMessage = (Messages) ois.readObject();
            String decriptedMessage= IntegrityConfidentiality.prepareForRecieve3(recievedMessage, sharedKey,"Client_Private_"+(new String(user.getUsername()))+".key");
            return decriptedMessage;
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return null;
    }

    public String sendingWithAuthIntegrityConfid(String clientMessage) {
        try {
            
            Messages messageToSend =
                IntegrityAuthenticationConfidentiality.prepareForSend1(clientMessage, "Client_Private_" + new String(user.getUsername()) + ".key", "Server_Public.key");
            oos.writeObject(messageToSend);
            oos.flush();

            Messages recievedMessage = (Messages)ois.readObject();
            String message =
                IntegrityAuthenticationConfidentiality.prepareForRecieve1(recievedMessage, "Server_Public.key", "Client_Private_" + new String(user.getUsername())+ ".key");
            return message;
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    
        
        
        return null;
    }
}
