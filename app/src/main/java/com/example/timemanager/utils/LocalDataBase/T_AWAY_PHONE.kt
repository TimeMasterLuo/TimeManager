package com.example.timemanager.utils.LocalDataBase

import android.media.RingtoneManager
import android.os.Parcelable
import com.example.timemanager.application.TimeManager
import kotlinx.android.parcel.Parcelize
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table

@Parcelize
@Table(name = "T_AWAY_PHONE")
data class T_AWAY_PHONE(
    @Column(name = "ID",isId = true)var ID:String="",
    @Column(name = "STARTDATE")var STARTDATE:String="",
    @Column(name = "ENDDATE")var ENDDATE:String="",
    @Column(name = "TIME")var TIME:String="",
    @Column(name = "COINS")var COINS:Int=0,
    @Column(name = "USERID")var USERID:String="",
    @Column(name = "USERNAME")var USERNAME:String="",
    @Column(name = "MODEL")var MODEL:String="normal"
) : Parcelable {

}