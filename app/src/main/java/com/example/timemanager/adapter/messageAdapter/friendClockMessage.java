package com.example.timemanager.adapter.messageAdapter;

public class friendClockMessage {
    private String id;
    private String toId;
    private String type;
    private String sender;
    private String receiver;
    private String status;
    private String clockStatus;
    private String updateTime;
    private String recordTime;
    private String task;
    private int score;
    private int coins;
    private String note;


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

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getNote() {
        if(note.equals(""))
        {
            return sender + "很懒，没有任何留言~";
        }
        else return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getClockStatus() {
        return clockStatus;
    }

    public void setClockStatus(String clockStatus) {
        this.clockStatus = clockStatus;
    }
}
