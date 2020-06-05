package com.qumasi.model;

import com.qumasi.model.data.Challenge;
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

public class ServerThread extends Thread {
    private Socket socket = null;
    private LoginInfor user=null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            byte[] clientMessage = (byte[]) ois.readObject();
            byte[] byPrivateKey = SecurityUtility.decriptDataByPrivateKey(clientMessage, "Server_Private.key");
            user = (LoginInfor) Utility.convertBytesToObject(byPrivateKey);
            System.out.println("Server -> Hello: " + new String(user.getUsername()));
            if (user.isNoSecurity()) {
                startChattingWithNoSecurity(ois, oos);
            }else if(user.isAuthentication()&&!user.isConfidentiality()&&!user.isIntegrity()){
                startChattingWithAuth(ois, oos);
            }else if(!user.isAuthentication()&&user.isConfidentiality()&&!user.isIntegrity()){
                startChattingWithConfid(ois, oos);
            }else if(!user.isAuthentication()&&!user.isConfidentiality()&&user.isIntegrity()){
                startChattingWithIntegrity(ois, oos);
            }else if(user.isAuthentication()&&user.isConfidentiality()&&!user.isIntegrity()){
                startChattingWithAuthConfid(ois, oos);
            }else if(user.isAuthentication()&&!user.isConfidentiality()&&user.isIntegrity()){
                startChattingWithAuthIntegrity(ois, oos);
            }else if(!user.isAuthentication()&&user.isConfidentiality()&&user.isIntegrity()){
                startChattingWithIntegrityConfid(ois, oos);
            }else if(user.isAuthentication()&&user.isConfidentiality()&&user.isIntegrity()){
                startChattingWithAuthIntegrityConfid(ois, oos);
            }
            this.socket.close();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
    }

    private void startChattingWithNoSecurity(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
            String message = null;
            System.out.println(message);
            while ((message = (String) ois.readObject()) != null) {
                oos.writeUTF(message);
                oos.flush();
                if (message.toLowerCase().trim().equals("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startChattingWithAuth(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
            Messages message = null;
            while ((message = (Messages) ois.readObject()) != null) { 
                String recievedMessage =Authentication.prepareForRecieve(message, "Client_Public_"+(new String(user.getUsername()))+".key");
                message = Authentication.prepareForSend(recievedMessage, "Server_Private.key");
                oos.writeObject(message);
                oos.flush();
                if (recievedMessage.toLowerCase().trim().equals("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startChattingWithConfid(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
            byte[] message = null;
            while ((message = (byte[]) ois.readObject()) != null) { 
                String recievedMessage =Confidentiality.prepareForRecieve(message, "Server_Private.key");
                message = Confidentiality.prepareForSend(recievedMessage, "Client_Public_"+new String(user.getUsername())+".key");
                oos.writeObject(message);
                oos.flush();
                if (recievedMessage.toLowerCase().trim().equals("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startChattingWithIntegrity(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
//------------------------step1             
            byte[] challenge=(byte[])ois.readObject();
            Challenge challenge1 = Integrity.prepareToRecieveChallenge(challenge, "Server_Private.key");
            System.out.println("============== recieve challenge: "+new String(challenge1.getChellange()));
            System.out.println(challenge1.getSecritKey());
//------------------------step2            
            byte[] encriptedChallenge=Integrity.prepareToSendChallenge(challenge1.getChellange(),challenge1.getSecritKey());
            byte[] decriptedChallenge=Integrity.prepareToReceiveChallenge3(encriptedChallenge,challenge1.getSecritKey());
            oos.writeObject(encriptedChallenge);
            oos.flush();
            
                   
             Messages message = null;
            while ((message = (Messages) ois.readObject()) != null) { 
                Messages recievedMessage =Integrity.prepareForRecieve(message, challenge1.getSecritKey());
                oos.writeObject(recievedMessage);
                oos.flush();
                if (new String(message.getMessage()).toLowerCase().trim().equals("bye"))
                    break;
            } 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    private void startChattingWithAuthConfid(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
            Messages message = null;
            while ((message = (Messages) ois.readObject()) != null) { 
                Messages recievedMessage =AuthenticationConfidentiality.prepareForRecieve(message, "Client_Public_"+(new String(user.getUsername()))+".key","Server_Private.key");
                byte[] messageToSend = AuthenticationConfidentiality.prepareForSend(recievedMessage, "Server_Private.key");
                oos.writeObject(messageToSend);
                oos.flush();
                String m=new String(recievedMessage.getMessage());
                if (m.toLowerCase().trim().equals("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startChattingWithAuthIntegrity(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
            Messages message = null;
            while ((message = (Messages) ois.readObject()) != null) { 
                Messages recievedMessage =IntegrityAuthentication.prepareForRecieve(message, "Client_Public_"+(new String(user.getUsername()))+".key");
                Messages messageToSend = IntegrityAuthentication.prepareForSend(recievedMessage, "Server_Private.key");
                oos.writeObject(messageToSend);
                oos.flush();
                
                String m=new String(recievedMessage.getMessage());
                if (m.toLowerCase().trim().equals("bye"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startChattingWithIntegrityConfid(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
        //------------------------step1
            byte[] challenge=(byte[])ois.readObject();
            Challenge challenge1 = Integrity.prepareToRecieveChallenge(challenge, "Server_Private.key");
            System.out.println("============== recieve challenge: "+new String(challenge1.getChellange()));
            System.out.println(challenge1.getSecritKey());
        //------------------------step2
            byte[] encriptedChallenge=Integrity.prepareToSendChallenge(challenge1.getChellange(),challenge1.getSecritKey());
            byte[] decriptedChallenge=Integrity.prepareToReceiveChallenge3(encriptedChallenge,challenge1.getSecritKey());
            oos.writeObject(encriptedChallenge);
            oos.flush();
            
                   
             Messages message = null;
            while ((message = (Messages) ois.readObject()) != null) { 
                Messages recievedMessage =IntegrityConfidentiality.prepareForRecieveAndSend(message, challenge1.getSecritKey(),"Server_Private.key", "Client_Public_"+(new String(user.getUsername()))+".key");
                oos.writeObject(recievedMessage);
                oos.flush();
                if (new String(message.getMessage()).toLowerCase().trim().equals("bye"))
                    break;
            } 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startChattingWithAuthIntegrityConfid(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("start");
            oos.flush();
            Messages message = null;
            while ((message = (Messages) ois.readObject()) != null) { 
                Messages receivedMessage =IntegrityAuthenticationConfidentiality.prepareForRecieve1(message, "Client_Public_"+(new String(user.getUsername()))+".key","Server_Private.key");
                Messages  messageToSend =
                    IntegrityAuthenticationConfidentiality.prepareForSend1(receivedMessage, "Server_Private.key", "Client_Public_"+(new String(user.getUsername()))+".key");
                oos.writeObject(messageToSend);
                oos.flush();
                
                
                
                
                
                
                
                
                String m=new String(receivedMessage.getMessage());
                if (m.toLowerCase().trim().equals("bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
