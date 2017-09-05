package me.chunsheng.kotlinlearn.second.model

import android.os.Parcel
import android.os.Parcelable


/**
 * Copyright © 2016 edaixi. All Rights Reserved.
 * Author: wei_spring
 * Date: 2017/8/23
 * Email:weichsh@edaixi.com
 * Function:
 */

data class DailyData(val sid: String, val tts: String, val content: String, val note: String,
                     val love: String, val translation: String, val picture: String, val picture2: String,
                     val caption: String, val dateline: String, val s_pv: String,
                     val sp_pv: String, val fenxiang_img: String) : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(this.sid)
        parcel.writeString(this.tts)
        parcel.writeString(this.content)
        parcel.writeString(this.note)
        parcel.writeString(this.love)
        parcel.writeString(this.translation)
        parcel.writeString(this.picture)
        parcel.writeString(this.picture2)
        parcel.writeString(this.caption)
        parcel.writeString(this.dateline)
        parcel.writeString(this.s_pv)
        parcel.writeString(this.sp_pv)
        parcel.writeString(this.fenxiang_img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DailyData> {
        override fun createFromParcel(parcel: Parcel): DailyData {
            return DailyData(parcel)
        }

        override fun newArray(size: Int): Array<DailyData?> {
            return arrayOfNulls(size)
        }
    }

}

