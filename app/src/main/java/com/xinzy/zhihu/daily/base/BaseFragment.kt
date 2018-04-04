package com.xinzy.zhihu.daily.base

import android.support.v4.app.Fragment

/**
 * Created by Xinzy on 2018/3/22.
 */
abstract class BaseFragment : Fragment(), BaseView {
    override fun showLoading() {

    }

    override fun closeLoading() {

    }

    override fun isActive() = !isResumed
}