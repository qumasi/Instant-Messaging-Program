package com.qumasi.model.data;

import java.io.Serializable;

public class Challenge implements Serializable {
    byte[] chellange;
    byte[] secritKey;

    public void setChellange(byte[] chellange) {
        this.chellange = chellange;
    }

    public byte[] getChellange() {
        return chellange;
    }

    public void setSecritKey(byte[] secritKey) {
        this.secritKey = secritKey;
    }

    public byte[] getSecritKey() {
        return secritKey;
    }
}
