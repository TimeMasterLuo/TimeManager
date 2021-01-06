package com.example.timemanager.adapter.messageAdapter;

public class addFriendMessage {
    private String id;
    private String type;
    private String sender;
    private String receiver;
    private String status;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
