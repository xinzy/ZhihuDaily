package com.xinzy.zhihu.daily.biz.main.contract

import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.base.BasePresenter
import com.xinzy.zhihu.daily.base.BaseView

/**
 * Created by Xinzy on 2018/3/28.
 */
interface ThemeConstract {

    interface Presenter : BasePresenter {
        fun load()

        fun reload()
    }

    interface View : BaseView {
        fun showList(list: List<MultiAdapter.ViewType>, isAdd: Boolean)
    }

}