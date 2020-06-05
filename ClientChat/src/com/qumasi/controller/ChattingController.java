package com.qumasi.controller;


import com.qumasi.model.ClientChat;
import com.qumasi.model.data.LoginInfor;
import com.qumasi.model.data.Messages;
import com.qumasi.view.ChattingView;

import java.awt.event.ActionEvent;

public class ChattingController {
    private ChattingView chattingView;
    private ClientChat client;
    private LoginInfor userInfor;
    private byte[] sharedKey;


    ChattingController(ClientChat client, LoginInfor userInfor) {
        this.client = client;
        this.userInfor = userInfor;
        this.chattingView = new ChattingView();
        this.chattingView.getJButton1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        this.chattingView.setVisible(true);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        String chatArea = this.chattingView.getJTextArea1().getText();
        String clientMessage = this.chattingView.getJTextField1().getText();
        this.chattingView.getJTextField1().setText("");
        if (userInfor.isNoSecurity()) {
            String echoByServer = this.client.sendingMessageWithNoSecurity(clientMessage);
            this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" + new String(userInfor.getUsername()) +
                                                      ") : " + clientMessage + "\n Server : " + echoByServer +
                                                      "\n ==========");
        } else if (userInfor.isAuthentication() && !userInfor.isConfidentiality() && !userInfor.isIntegrity()) {
            String echoByServer = this.client.sendingWithAuth(clientMessage);
            this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" + new String(userInfor.getUsername()) +
                                                      ") : " + clientMessage + "\n Server : " + echoByServer +
                                                      "\n ==========");
        } else if (!userInfor.isAuthentication() && userInfor.isConfidentiality() && !userInfor.isIntegrity()) {
            String echoByServer = this.client.sendingWithConfid(clientMessage);
            this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" + new String(userInfor.getUsername()) +
                                                      ") : " + clientMessage + "\n Server : " + echoByServer +
                                                      "\n ==========");
        } else if (!userInfor.isAuthentication() && !userInfor.isConfidentiality() && userInfor.isIntegrity()) {
            Messages message = this.client.sendingWithIntegrity(clientMessage);
            if (message == null)
                this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" +
                                                          new String(userInfor.getUsername()) + ") : " + clientMessage +
                                                          "\n Server : " + "Problem with integrity" + "\n ==========");
            else
                this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" +
                                                          new String(userInfor.getUsername()) + ") : " + clientMessage +
                                                          "\n Server : " + new String(message.getMessage()) +
                                                          "\n ==========");
        } else if (userInfor.isAuthentication() && userInfor.isConfidentiality() && !userInfor.isIntegrity()) {
            String message = this.client.sendingWithAuthConfid(clientMessage);
            this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" + new String(userInfor.getUsername()) +
                                                      ") : " + clientMessage + "\n Server : " + message +
                                                      "\n ==========");

        } else if (userInfor.isAuthentication() && !userInfor.isConfidentiality() && userInfor.isIntegrity()) {
            String message = this.client.sendingWithAuthIntegrity(clientMessage);
            this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" + new String(userInfor.getUsername()) +
                                                      ") : " + clientMessage + "\n Server : " + message +
                                                      "\n ==========");

        } else if (!userInfor.isAuthentication() && userInfor.isConfidentiality() && userInfor.isIntegrity()) {
            String message = this.client.sendingWithIntegrityConfid(clientMessage);
            this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" + new String(userInfor.getUsername()) +
                                                      ") : " + clientMessage + "\n Server : " + message +
                                                      "\n ==========");

        }
        else if (userInfor.isAuthentication() && userInfor.isConfidentiality() && userInfor.isIntegrity()) {
                    String message = this.client.sendingWithAuthIntegrityConfid(clientMessage);
                    this.chattingView.getJTextArea1().setText(chatArea + "\n Client(" + new String(userInfor.getUsername()) +
                                                              ") : " + clientMessage + "\n Server : " + message +
                                                              "\n ==========");

                }
    }
}
