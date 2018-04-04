package com.xinzy.zhihu.daily.base

/**
 * Created by Xinzy on 2018/3/22.
 */
interface BaseView {

    fun showLoading()

    fun closeLoading()

    fun isActive() : Boolean

}