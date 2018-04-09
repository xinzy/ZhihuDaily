package com.xinzy.zhihu.daily.biz.main.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.Keep
import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.util.VIEW_TYPE_STORY
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Xinzy on 2018/3/23.
 */

@Keep
class Story() : MultiAdapter.ViewType, Parcelable {

    var image = ""
    var images: List<String> = mutableListOf()
    var id = 0
    var title = ""

    constructor(parcel: Parcel) : this() {
        image = parcel.readString()
        images = parcel.createStringArrayList()
        id = parcel.readInt()
        title = parcel.readString()
    }

    fun hasImage() = !TextUtils.isEmpty(image) || !images.isEmpty()

    fun img(): String = if (!TextUtils.isEmpty(image)) image else if (!images.isEmpty()) images[0] else ""

    override fun getItemType() = VIEW_TYPE_STORY

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeStringList(images)
        parcel.writeInt(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Story> {
        override fun createFromParcel(parcel: Parcel): Story {
            return Story(parcel)
        }

        override fun newArray(size: Int): Array<Story?> {
            return arrayOfNulls(size)
        }
    }
}

@Keep
class News {
    var date = ""
    var stories: List<Story> = mutableListOf()

    @SerializedName ("top_stories")
    var top: List<Story> = mutableListOf()

    var timestamp = 0L
    var name = ""

    fun hasTops() = !top.isEmpty()

    fun date(): String {
        val d = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse(date)
        val formater = SimpleDateFormat("yy年MM月dd日 E", Locale.getDefault())
        return formater.format(d)
    }
}

@Keep
class Editor {
    var id = 0
    var url = ""
    var bio = ""
    var avatar = ""
    var name = ""
}

@Keep
class ThemeDaily {
    var stories = arrayListOf<Story>()
    var description = ""
    var background = ""
    var name = ""
    var image = ""
    var editors = arrayListOf<Editor>()

    fun hasStory() = stories.isNotEmpty()
}