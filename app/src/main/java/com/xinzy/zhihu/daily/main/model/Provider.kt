package com.xinzy.zhihu.daily.main.model

import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.util.VIEW_TYPE_EDITOR
import com.xinzy.zhihu.daily.util.VIEW_TYPE_THEME_TOP
import com.xinzy.zhihu.daily.util.VIEW_TYPE_TITLE
import com.xinzy.zhihu.daily.util.VIEW_TYPE_TOP_STORIES

/**
 * Created by Xinzy on 2018/3/28.
 */

class Top(topStories: List<Story>) : MultiAdapter.ViewType {
    val mTopStories = topStories

    override fun getItemType() = VIEW_TYPE_TOP_STORIES
}

class Title(title: String) : MultiAdapter.ViewType {
    val mTitle = title

    override fun getItemType() = VIEW_TYPE_TITLE
}

class ThemeTop(desc: String, url: String) : MultiAdapter.ViewType {
    val mDesc = desc
    val mImageUrl = url

    override fun getItemType() = VIEW_TYPE_THEME_TOP
}

class Editors(list: List<Editor>) : MultiAdapter.ViewType {
    val editors = list

    override fun getItemType() = VIEW_TYPE_EDITOR
}