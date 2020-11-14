package com.example.timemanager.utils.LocalDataBase

import android.media.RingtoneManager
import android.os.Parcelable
import com.example.timemanager.Home
import com.example.timemanager.TimeManager
import kotlinx.android.parcel.Parcelize
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table
import java.sql.Date


@Parcelize
@Table(name = "T_ALARM_CLOCK")
data class T_ALARM_CLOCK(
        @Column(name = "ID",isId = true)var ID:String="",
        @Column(name = "TIME")var TIME:String="",
        @Column(name = "TIMESTAMP")var TIMESTAMP:String="",
        @Column(name = "Date")var Date:String="",
        @Column(name = "NOTE")var NOTE:String="",
        @Column(name = "task")var Task:String="None",
        @Column(name = "FROM")var FROM:String="me",
        @Column(name = "TO")var TO:String="me",
        @Column(name = "SOUND")var SOUND:String=RingtoneManager.
                getActualDefaultRingtoneUri(
                                TimeManager.instance(),RingtoneManager.TYPE_RINGTONE).toString(),
        @Column(name = "UPDATE_TIME")var UPDATE_TIME:String="",
        @Column(name = "ACTIVE")var ACTIVE:String="1"
        ) : Parcelable{

}