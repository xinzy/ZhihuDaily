package com.xinzy.zhihu.daily.main.contract

import com.xinzy.zhihu.daily.base.BasePresenter
import com.xinzy.zhihu.daily.base.BaseView
import com.xinzy.zhihu.daily.main.model.Theme

/**
 * Created by Xinzy on 2018/3/22.
 */
interface MainContract {

    interface Presenter : BasePresenter {
        fun loadTheme()

        fun getThemeById(id: Int): Theme?
    }

    interface View : BaseView {
        fun showTheme(themes: List<Theme>)
    }
}