package com.example.timemanager.utils.clock;

import java.io.Serializable;
import java.util.Date;

public class Clock implements Serializable {
    private String type;
    private String set_person;
    private Date date;
    private String set_time;
    private String time_to_finish;
    private String grade;
    private Integer coins;
    private String notes;

    public Clock(String type,String set_person,Date date,String set_time,String time_to_finish,String grade,Integer coins,String notes) {
        this.type = type;
        this.set_person = set_person;
        this.set_time = set_time;
        this.date = date;
        this.time_to_finish = time_to_finish;
        this.grade = grade;
        this.coins = coins;
        this.notes = notes;
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

    public String getSet_time() {
        return set_time;
    }

    public String getTime_to_finish() {
        return time_to_finish;
    }
}
