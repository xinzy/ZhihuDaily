package com.xinzy.zhihu.daily.main.contract

import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.base.BasePresenter
import com.xinzy.zhihu.daily.base.BaseView

/**
 * Created by Xinzy on 2018/3/23.
 */
interface IndexContract {

    interface Presenter : BasePresenter {
        fun load()

        fun reload()
    }

    interface View : BaseView {
        fun show(list: List<MultiAdapter.ViewType>, isAdd: Boolean)
    }
}