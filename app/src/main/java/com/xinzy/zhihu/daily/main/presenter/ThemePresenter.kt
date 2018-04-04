package com.xinzy.zhihu.daily.main.presenter

import com.google.gson.reflect.TypeToken
import com.xinzy.http.RequestCallback
import com.xinzy.http.SmartHttp
import com.xinzy.http.SmartHttpException
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.main.contract.ThemeConstract
import com.xinzy.zhihu.daily.main.model.Editors
import com.xinzy.zhihu.daily.main.model.ThemeDaily
import com.xinzy.zhihu.daily.main.model.ThemeTop
import com.xinzy.zhihu.daily.util.themeDaily

/**
 * Created by Xinzy on 2018/3/28.
 */
class ThemePresenter(view: ThemeConstract.View, id: Int) : ThemeConstract.Presenter {

    private val mView: ThemeConstract.View = view
    private val mThemeId: Int = id

    private var isReload = false
    private var isFirstPage = true
    private var lastId = 0

    companion object {
        private val CACHE_KEY_THEME = "CACHE_KEY_THEME-"
        private fun cacheKey(id: Int) = CACHE_KEY_THEME + id
    }

    override fun load() {
        val url = themeDaily(mThemeId, lastId)
        val http = SmartHttp.get(url)
        if (lastId == 0) {
            http.cacheModel(SmartHttp.FIRST_CACHE_THEN_REQUEST).cacheKey(cacheKey(mThemeId))
            mView.showLoading()
        }

        val cache = http.enqueue(object : TypeToken<ThemeDaily>(){}, object : RequestCallback<ThemeDaily> {
            override fun onSuccess(t: ThemeDaily?) {
                mView.closeLoading()
                show(t)
                isFirstPage = false
            }

            override fun onFailure(e: SmartHttpException?) {
                mView.closeLoading()
            }
        })
        if (isFirstPage) {
            if (cache != null) {
                if (!isReload) show(cache)
            } else {
                mView.showLoading()
            }
        }
        isReload = false
    }

    override fun reload() {
        isReload = true
        isFirstPage = true
        lastId = 0

        load()
    }

    private fun show(data: ThemeDaily?) {
        data?.let {
            val list = arrayListOf<MultiAdapter.ViewType>()
            if (isFirstPage) {
                list.add(ThemeTop(data.description, data.image))
                list.add(Editors(data.editors))
            }
            list.addAll(data.stories)
            mView.showList(list, !isFirstPage)
            if (data.hasStory()) {
                lastId = data.stories.last().id
            }
        }
    }
}