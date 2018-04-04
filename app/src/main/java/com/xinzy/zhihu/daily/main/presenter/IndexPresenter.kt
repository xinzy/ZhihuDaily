package com.xinzy.zhihu.daily.main.presenter

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.xinzy.http.RequestCallback
import com.xinzy.http.SmartHttp
import com.xinzy.http.SmartHttpException
import com.xinzy.widget.MultiAdapter
import com.xinzy.zhihu.daily.main.contract.IndexContract
import com.xinzy.zhihu.daily.main.model.News
import com.xinzy.zhihu.daily.main.model.Title
import com.xinzy.zhihu.daily.main.model.Top
import com.xinzy.zhihu.daily.util.API_NEWS_LATEST
import com.xinzy.zhihu.daily.util.newsBefore

/**
 * Created by Xinzy on 2018/3/23.
 */
class IndexPresenter(view: IndexContract.View) : IndexContract.Presenter, RequestCallback<News> {

    private var date = ""
    private var mView: IndexContract.View = view

    private var isFirstPage = true
    private var isRefresh = false

    companion object {
        val CACHE_KEY_LATEST = "CACHE_KEY_LATEST"
    }

    override fun load() {
        isFirstPage = if (isRefresh) true else TextUtils.isEmpty(date)
        val url = if (isFirstPage) API_NEWS_LATEST else newsBefore(date)

        val http = SmartHttp.get(url)
        if (isFirstPage) {
            http.cacheKey(CACHE_KEY_LATEST).cacheModel(SmartHttp.FIRST_CACHE_THEN_REQUEST)
        }
        val news = http.enqueue(object : TypeToken<News>(){}, this)
        if (isFirstPage && !isRefresh) {
            if (news != null) show(news)
            else mView.showLoading()
        }
        isRefresh = false
    }

    override fun reload() {
        isRefresh = true
        load()
    }

    override fun onSuccess(t: News?) {
        mView.closeLoading()
        if (t != null) {
            show(t)
            date = t.date
        }
    }

    override fun onFailure(e: SmartHttpException?) {
        mView.closeLoading()
    }

    private fun show(news: News) {
        val list = mutableListOf<MultiAdapter.ViewType>()
        if (news.hasTops()) list.add(Top(news.top))
        if (isFirstPage) {
            list.add(Title("今日消息"))
        } else {
            list.add(Title(news.date()))
        }
        list.addAll(news.stories)

        mView.show(list, !isFirstPage)
    }
}