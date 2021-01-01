package com.example.timemanager.utils.clock;

import java.io.Serializable;
import java.util.Date;

public class Clock implements Serializable {
    private final Integer id;
    private final String kind;
    private final String type;
    private final String set_person;
    private final String user;
    private final String task;
    private final Date start_time;
    private final Date end_time;
    private final String last_time;
    private final String status;
    private final String grade;
    private final Integer coins;
    private final String notes;

    public Clock(Integer id,String kind,String type,String task,String set_person,Date start_time,String status,String grade,Integer coins,String notes,String user) {
        this.id=id;
        this.kind=kind;
        this.type = type;
        this.task=task;
        this.set_person = set_person;
        this.start_time= start_time;
        this.end_time=null;
        this.status = status;
        this.grade = grade;
        this.coins = coins;
        this.notes = notes;
        this.user=user;
        this.last_time=null;
    }
    public Clock(Integer id,String kind,String type,Date start_time,Date end_time,String user,String last_time,Integer coins){
        this.id=id;
        this.kind=kind;
        this.type = type;
        this.task=null;
        this.set_person = null;
        this.start_time= start_time;
        this.end_time=end_time;
        this.status = null;
        this.grade = null;
        this.coins = coins;
        this.notes = null;
        this.user=user;
        this.last_time=last_time;
    }

    public Integer getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getTask() {
        return task;
    }

    public String getLast_time() {
        return last_time;
    }

    public String getUser() {
        return user;
    }

    public String getType() {
        return type;
    }

    public Date getStart_time() {
        return start_time;
    }

    public Date getEnd_time() {
        return end_time;
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

    public String getStatus() {
        return status;
    }
}
