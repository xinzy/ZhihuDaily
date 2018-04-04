package com.xinzy.zhihu.daily.base

import android.support.v7.app.AppCompatActivity

/**
 * Created by Xinzy on 2018/3/22.
 */

abstract class BaseActivity: AppCompatActivity(), BaseView {

    override fun showLoading() {
    }

    override fun closeLoading() {
    }

    override fun isActive() = !isFinishing
}