package com.example.se_backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "AlarmClock", schema = "SE-DB", catalog = "")
public class AlarmClockEntity {
    private Timestamp updateTime;
    private Timestamp recordTime;
    private Timestamp startTime;
    private Integer credit;
    private String taskType;
    private String status;
    private String message;
    private Integer id;
    private Integer getter_id;
    private Integer setter_id;
    private String isActive;
    private String sound;



    @Basic
    @Column(name = "getter_id", nullable = true)
    public Integer getGetter_id() {
        return getter_id;
    }
    public void setGetter_id(Integer getter_id) {
        this.getter_id = getter_id;
    }


    @Basic
    @Column(name = "setter_id", nullable = true)
    public Integer getSetter_id() {
        return setter_id;
    }
    public void setSetter_id(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "record_time", nullable = true)
    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }

    @Basic
    @Column(name = "start_time", nullable = true)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "credit", nullable = true)
    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    @Basic
    @Column(name = "task_type", nullable = true, length = 255)
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "message", nullable = true, length = 255)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "is_active", nullable = true, length = 255)
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Basic
    @Column(name = "sound", nullable = true, length = 255)
    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmClockEntity that = (AlarmClockEntity) o;
        return id == that.id &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(recordTime, that.recordTime) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(credit, that.credit) &&
                Objects.equals(taskType, that.taskType) &&
                Objects.equals(status, that.status) &&
                Objects.equals(message, that.message) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(sound, that.sound) &&
                Objects.equals(getter_id,this.getter_id) &&
                Objects.equals(setter_id,this.setter_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateTime, recordTime, startTime, credit, taskType, status, message, id, isActive, sound,setter_id,getter_id);
    }
}
