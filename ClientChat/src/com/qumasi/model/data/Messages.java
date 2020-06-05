package com.qumasi.model.data;

import java.io.Serializable;


public class Messages implements Serializable{
    private byte[] message;
    private byte[] hash;
    private byte[] key;


    public void setMessage(byte[] message) {
        this.message = message;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getKey() {
        return key;
    }
}
