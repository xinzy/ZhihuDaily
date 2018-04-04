package com.xinzy.zhihu.daily.util

/**
 * Created by Xinzy on 2018/3/22.
 */



private const val BASE_API = "https://news-at.zhihu.com/api/"

/**
 * 获取日报主题列表
 */
const val API_THEME = BASE_API + "4/themes"

/**
 * 最新日报
 */
const val API_NEWS_LATEST = BASE_API + "4/news/latest"

/**
 * 最新日报
 */
const val API_NEWS_BEFORE = BASE_API + "4/news/before/"

/**
 * 获取消息内容
 */
const val API_DETAIL = BASE_API + "4/news/"

/**
 * 指定主题的文章列表
 */
const val API_THEME_STORY = BASE_API + "4/theme"




fun newsBefore(date: String) = API_NEWS_BEFORE + date

fun detail(id: Int) = API_DETAIL + id

fun themeDaily(theme: Int, id: Int = 0) = if (id == 0) "$API_THEME_STORY/$theme" else "$API_THEME_STORY/$theme/before/$id"









const val VIEW_TYPE_STORY = "STORY"
const val VIEW_TYPE_TOP_STORIES = "TOP_STORIES"
const val VIEW_TYPE_TITLE = "TITLE"
const val VIEW_TYPE_THEME_TOP = "THEME_TOP"
const val VIEW_TYPE_EDITOR = "EDITOR"

const val EVENT_ITEM_CLICK: Short = 1
const val EVENT_CAROUSEL_ITEM_CLICK: Short = 2
const val EVENT_THEME_TOP_CLICK: Short = 3
