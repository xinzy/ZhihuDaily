package com.xinzy.zhihu.daily.detail.presenter

import com.google.gson.reflect.TypeToken
import com.xinzy.http.RequestCallback
import com.xinzy.http.SmartHttp
import com.xinzy.http.SmartHttpException
import com.xinzy.zhihu.daily.detail.constact.DetailConstact
import com.xinzy.zhihu.daily.detail.model.Detail
import com.xinzy.zhihu.daily.util.detail

/**
 * Created by Xinzy on 2018/3/27.
 */

class DetailPresenter(view: DetailConstact.View) : DetailConstact.Presenter {

    private var mView: DetailConstact.View = view

    companion object {
        private val CACHE_KEY_DETAIL = "DETAIL-"
        private fun cacheKey(id: Int) = CACHE_KEY_DETAIL + id
    }

    override fun load(id: Int) {
        val http = SmartHttp.get(detail(id)).cacheKey(cacheKey(id)).cacheModel(SmartHttp.FIRST_CACHE_THEN_REQUEST)

        val cache = http.enqueue(object : TypeToken<Detail>(){}, object : RequestCallback<Detail> {
            override fun onSuccess(t: Detail?) {
                show(t)
            }

            override fun onFailure(e: SmartHttpException?) {
            }
        })
        cache?.let { show(cache) }
    }

    private fun show(detail: Detail?) {
        detail?.let {
            mView.show(detail)
        }
    }

}