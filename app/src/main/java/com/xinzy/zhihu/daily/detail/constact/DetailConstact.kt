package com.xinzy.zhihu.daily.detail.constact

import com.xinzy.zhihu.daily.base.BasePresenter
import com.xinzy.zhihu.daily.base.BaseView
import com.xinzy.zhihu.daily.detail.model.Detail

/**
 * Created by Xinzy on 2018/3/27.
 */

interface DetailConstact {

    interface Presenter : BasePresenter {
        fun load(id: Int)
    }

    interface View : BaseView {
        fun show(detail: Detail)
    }

}