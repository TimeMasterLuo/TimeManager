package com.example.timemanager.utils.LocalDataBase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.xutils.db.annotation.Column
import org.xutils.db.annotation.Table

//后端不储存白名单
@Parcelize
@Table(name = "T_WHITELIST")
data class T_WHITELIST(
    @Column(name = "PACKAGENAME",isId = true)var PACKAGENAME:String="",
    @Column(name = "NAME")var NAME:String="",
    //@Column(name = "ICON")var ICON:ByteArray = ByteArray(0),
    @Column(name = "PACKAGEPATH")var PACKAGEPATH:String="",
    @Column(name = "VERSIONNAME")var VERSIONNAME:String="",
    @Column(name = "VERSIONCODE")var VERSIONCODE:Int= 0,
    @Column(name = "ISSYSTEM")var ISSYSTEM:Boolean= false
) : Parcelable{

}