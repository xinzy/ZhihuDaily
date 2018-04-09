package com.xinzy.zhihu.daily.biz.main.presenter

import com.google.gson.reflect.TypeToken
import com.xinzy.http.RequestCallback
import com.xinzy.http.SmartHttp
import com.xinzy.http.SmartHttpException
import com.xinzy.zhihu.daily.biz.main.contract.MainContract
import com.xinzy.zhihu.daily.biz.main.model.Theme
import com.xinzy.zhihu.daily.biz.main.model.ThemeBean
import com.xinzy.zhihu.daily.biz.main.model.getDefaultTheme
import com.xinzy.zhihu.daily.util.API_THEME

/**
 * Created by Xinzy on 2018/3/22.
 */
class MainPresenter(private val mView: MainContract.View) : MainContract.Presenter {

    private var mThemes: List<Theme>? = null

    companion object {
        val THEME_CACHE_KEY = "THEME_CACHE_KEY"
    }

    override fun loadTheme() {
        val cache = SmartHttp.get(API_THEME).cacheKey(THEME_CACHE_KEY).cacheModel(SmartHttp.FIRST_CACHE_THEN_REQUEST)
                .enqueue(object: TypeToken<ThemeBean>(){}, object: RequestCallback<ThemeBean> {
                    override fun onSuccess(t: ThemeBean?) {
                        show(t)
                    }

                    override fun onFailure(e: SmartHttpException?) {
                    }
                })
        show(cache)
    }

    private fun show(bean: ThemeBean?) {
        val list = mutableListOf(getDefaultTheme())
        bean?.others?.forEach({it -> list.add(it)})

        mThemes = list
        mView.showTheme(list)
    }

    override fun getThemeById(id: Int): Theme? {
        mThemes?.forEach { if (it.id == id) return it }
        return null
    }
}