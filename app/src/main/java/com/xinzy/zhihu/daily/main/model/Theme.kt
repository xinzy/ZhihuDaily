package com.xinzy.zhihu.daily.main.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Keep

/**
 * Created by Xinzy on 2018/3/22.
 */

@Keep
class Theme : Parcelable {
    var color = 0
    var thumbnail = ""
    var description = ""
    var id = 0
    var name = ""

    constructor(parcel: Parcel) : this() {
        color = parcel.readInt()
        thumbnail = parcel.readString()
        description = parcel.readString()
        id = parcel.readInt()
        name = parcel.readString()
    }

    constructor()

    constructor(name: String) {
        this.name = name
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(color)
        parcel.writeString(thumbnail)
        parcel.writeString(description)
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Theme> {
        override fun createFromParcel(parcel: Parcel): Theme {
            return Theme(parcel)
        }

        override fun newArray(size: Int): Array<Theme?> {
            return arrayOfNulls(size)
        }
    }
}

@Keep
class ThemeBean {
    var limit = 0
    var others: List<Theme>? = null
}


fun getDefaultTheme() = Theme("首页")

