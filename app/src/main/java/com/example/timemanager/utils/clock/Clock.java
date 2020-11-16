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

    public Clock(String type,String set_person,Date date,String set_time,String time_to_finish,String grade,Integer coins,String notes){
        this.type=type;
        this.set_person=set_person;
        this.set_time=set_time;
        this.date=date;
        this.time_to_finish=time_to_finish;
        this.grade=grade;
        this.coins=coins;
        this.notes=notes;
    }
}
