package com.example.timemanager.utils.LocalDataBase

import android.media.RingtoneManager
import android.os.Parcelable
import com.example.timemanager.application.TimeManager
import kotlinx.android.parcel.Parcelize
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table


@Parcelize
@Table(name = "T_MESSAGE")
data class T_MESSAGE(
    @Column(name = "ID",isId = true)var ID:Int=0,
    @Column(name = "TYPE")var TYPE:String="好友请求", // 还有一种 “好友闹钟”
    @Column(name = "SENDER")var SENDER:String="",
    @Column(name = "RECEIVER")var RECEIVER:String="",
    @Column(name = "STATUS")var STATUS:String="待处理" // 已同意、已拒绝

) : Parcelable{

}