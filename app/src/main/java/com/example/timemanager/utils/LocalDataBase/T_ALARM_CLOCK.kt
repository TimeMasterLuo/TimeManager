package com.example.timemanager.utils.LocalDataBase

import android.media.RingtoneManager
import android.os.Parcelable
import com.example.timemanager.application.TimeManager
import kotlinx.android.parcel.Parcelize
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table


@Parcelize
@Table(name = "T_ALARM_CLOCK")
data class T_ALARM_CLOCK(
    @Column(name = "ID",isId = true)var ID:Int=0,
    @Column(name = "TIME")var TIME:String="",//后端不用存
    @Column(name = "TIMESTAMP")var TIMESTAMP:String="",
    @Column(name = "Date")var Date:String="",//后端不用存
    @Column(name = "RemoteID",isId = false)var RemoteID:Int=-1,
    @Column(name = "NOTE")var NOTE:String="",
    @Column(name = "Score")var Score:Int=0,
    @Column(name = "Coins")var Coins:String="0",
    @Column(name = "task")var Task:String="无",
    @Column(name = "FROM")var FROM:String=TimeManager.instance().username,
    @Column(name = "TO")var TO:String=TimeManager.instance().username,
    @Column(name = "ToID",isId = false)var ToID:Int=TimeManager.instance().uid.toInt(),
    @Column(name = "SOUND")var SOUND:String=RingtoneManager.//后端不用存
                getActualDefaultRingtoneUri(
                                TimeManager.instance(),RingtoneManager.TYPE_RINGTONE).toString(),
    @Column(name = "UPDATE_TIME")var UPDATE_TIME:String="",
    @Column(name = "STATUS")var Status:String="unfinished",
    @Column(name = "ACTIVE")var ACTIVE:String="1"
        ) : Parcelable{

}