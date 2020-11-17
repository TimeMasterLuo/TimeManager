package com.example.timemanager.utils.clock;

import java.io.Serializable;
import java.util.Date;

public class Clock implements Serializable {
    private String type;
    private String set_person;
    private String user;
    private Date date;
    private String time_to_finish;
    private String grade;
    private Integer coins;
    private String notes;

    public Clock(String type,String set_person,Date date,String time_to_finish,String grade,Integer coins,String notes,String user) {
        this.type = type;
        this.set_person = set_person;
        this.date = date;
        this.time_to_finish = time_to_finish;
        this.grade = grade;
        this.coins = coins;
        this.notes = notes;
        this.user=user;
    }

    public String getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public Integer getCoins() {
        return coins;
    }

    public String getGrade() {
        return grade;
    }

    public String getNotes() {
        return notes;
    }

    public String getSet_person() {
        return set_person;
    }

    public String getTime_to_finish() {
        return time_to_finish;
    }
}
