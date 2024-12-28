package com.indeed.virgil.example.models;

public record GenerateMessagePayload(long num, boolean sendToDlq) {
    public GenerateMessagePayload {
    }

    public long getNum() {
        return num;
    }

    public boolean getSendToDlq() {
        return this.sendToDlq;
    }
}
