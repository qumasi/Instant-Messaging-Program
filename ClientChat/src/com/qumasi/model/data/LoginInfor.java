package com.qumasi.model.data;

import java.io.Serializable;

public class LoginInfor implements Serializable {
    private byte[] username;
    private boolean noSecurity;
    private boolean authentication;
    private boolean confidentiality;
    private boolean integrity;


    public void setUsername(byte[] username) {
        this.username = username;
    }

    public byte[] getUsername() {
        return username;
    }

    public boolean isNoSecurity() {
        if(!isAuthentication()&& !isConfidentiality()&& !isIntegrity())
            return true;
        return false;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    public boolean isAuthentication() {   
        return authentication;
    }

    public void setConfidentiality(boolean confidentiality) {
        this.confidentiality = confidentiality;
    }

    public boolean isConfidentiality() {
        return confidentiality;
    }

    public void setIntegrity(boolean integrity) {
        this.integrity = integrity;
    }

    public boolean isIntegrity() {
        return integrity;
    }
}
