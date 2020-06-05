package com.qumasi.controller;


import com.qumasi.model.ClientChat;
import com.qumasi.model.data.LoginInfor;
import com.qumasi.view.RegisterView;

import java.awt.event.ActionEvent;


public class RegisterController {
    private RegisterView registerView;
    private ClientChat client;
    private LoginInfor loginInfor;


    public RegisterController(RegisterView registerView) {
        this.client = new ClientChat();
        this.loginInfor = new LoginInfor();
        this.registerView = registerView;
        this.registerView.getJButton1().addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        this.registerView.setVisible(true);
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        loginInfor.setUsername(this.registerView.getJTextField1().getText().getBytes());
        loginInfor.setAuthentication(this.registerView.getJCheckBox1().isSelected());
        loginInfor.setIntegrity(this.registerView.getJCheckBox2().isSelected());
        loginInfor.setConfidentiality(this.registerView.getJCheckBox3().isSelected());
        startup(loginInfor);
    }

    private void startup(LoginInfor loginInfor) {
        client.openConnection();
        Object loginResponse = client.login(loginInfor);
        if (loginInfor.isNoSecurity()) {
            String loginStatus = (String) loginResponse;
            if (loginStatus.equals("start")) {
                new ChattingController(client, loginInfor);
            }

        } else
            new ChattingController(client, loginInfor);
    }

}
